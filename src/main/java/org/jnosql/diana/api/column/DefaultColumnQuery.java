/*
 * Copyright 2017 Otavio Santana and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package org.jnosql.diana.api.column;


import org.jnosql.diana.api.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Defualt implementation of {@link ColumnQuery}
 */
class DefaultColumnQuery implements ColumnQuery {

    private final String columnFamily;

    private ColumnCondition condition;

    private final List<Sort> sorts = new ArrayList<>();

    private final List<String> columns = new ArrayList<>();

    private long maxResults = -1L;

    private long firstResult;

    private DefaultColumnQuery(String columnFamily) {
        this.columnFamily = requireNonNull(columnFamily, "column family is required");
    }


    static DefaultColumnQuery of(String columnFamily) {
        return new DefaultColumnQuery(columnFamily);
    }


    @Override
    public ColumnQuery and(ColumnCondition condition) throws NullPointerException {
        requireNonNull(condition, "condition is required");
        if (Objects.isNull(this.condition)) {
            this.condition = condition;
        } else {
            this.condition = this.condition.and(condition);
        }
        return this;
    }


    @Override
    public ColumnQuery or(ColumnCondition condition) throws NullPointerException {
        requireNonNull(condition, "condition is required");
        if (Objects.isNull(this.condition)) {
            this.condition = condition;
        } else {
            this.condition = this.condition.or(condition);
        }
        return this;
    }

    @Override
    public ColumnQuery withCondition(ColumnCondition condition) throws NullPointerException {
        this.condition = Objects.requireNonNull(condition, "condition is required");
        return this;
    }

    @Override
    public ColumnQuery withFirstResult(long firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    @Override
    public ColumnQuery withMaxResults(long maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    @Override
    public long getMaxResults() {
        return maxResults;
    }

    @Override
    public long getFirstResult() {
        return firstResult;
    }

    @Override
    public ColumnQuery addSort(Sort sort) throws NullPointerException {
        this.sorts.add(requireNonNull(sort, "Sort is required"));
        return this;
    }

    @Override
    public ColumnQuery addColumn(String column) throws NullPointerException {
        this.columns.add(requireNonNull(column, "column is required"));
        return this;
    }

    @Override
    public String getColumnFamily() {
        return columnFamily;
    }

    @Override
    public Optional<ColumnCondition> getCondition() {
        return Optional.ofNullable(condition);
    }

    @Override
    public List<String> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    @Override
    public List<Sort> getSorts() {
        return Collections.unmodifiableList(sorts);
    }

    @Override
    public ColumnDeleteQuery toDeleteQuery() {
        ColumnDeleteQuery columnDeleteQuery = ColumnDeleteQuery.of(columnFamily, condition);
        if (!columns.isEmpty()) {
            columnDeleteQuery.addAll(columns);
        }
        return columnDeleteQuery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColumnQuery)) {
            return false;
        }
        ColumnQuery that = (ColumnQuery) o;
        return maxResults == that.getMaxResults() &&
                firstResult == that.getFirstResult() &&
                Objects.equals(columnFamily, that.getColumnFamily()) &&
                Objects.equals(condition, that.getCondition()) &&
                Objects.equals(sorts, that.getSorts()) &&
                Objects.equals(columns, that.getColumns());
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnFamily, condition, sorts, columns, maxResults, firstResult);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultColumnQuery{");
        sb.append("columnFamily='").append(columnFamily).append('\'');
        sb.append(", condition=").append(condition);
        sb.append(", sorts=").append(sorts);
        sb.append(", columns=").append(columns);
        sb.append(", maxResults=").append(maxResults);
        sb.append(", firstResult=").append(firstResult);
        sb.append('}');
        return sb.toString();
    }
}
