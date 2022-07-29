package com.example.jetpackcomponentsapp.sqlite

object SQLQueryHelper{

    fun createTable(tableName : String, columnInformation : String) : String {
        return "CREATE TABLE $tableName (${columnInformation});"
    }

    fun dropTable(tableName : String) : String {
        return "DROP TABLE $tableName"
    }

    fun dropTableIfExist(table : String) : String {
        return "DROP TABLE IF EXIST $table"
    }

    fun insertInto(tableName : String, columns : String, values : String) : String {
        return "INSERT INTO $tableName ($columns) VALUES ($values)"
    }

    fun selectWhere(tableName : String, columnName : String, operand : String, criteria : String) : String? {
        return "SELECT * from $tableName WHERE $columnName $operand '$criteria' "
    }

    fun selectWhere(tableName : String, columnName : String, operand : String, criteria : Long) : String? {
        return "SELECT * from $tableName WHERE $columnName $operand $criteria "
    }

    fun selectWhere(tableName : String, columnName : String, operand : String, criteria : Int) : String? {
        return "SELECT * from $tableName WHERE $columnName $operand $criteria "
    }

    fun selectWhereLimit(tableName : String, columnName : String, operand : String, criteria : String, limit : Long) : String? {
        return "SELECT * from $tableName WHERE $columnName $operand '$criteria' Limit $limit"
    }

    fun selectWhereLikeLimit(tableName : String, columnName : String, criteria : String, limit : Long) : String? {
        return "SELECT * from $tableName WHERE UPPER($columnName) like  UPPER('%$criteria%') Limit $limit"
    }

    fun selectWhereLimit(tableName : String, columnName : String, operand : String, criteria : Long, limit : Long) : String? {
        return "SELECT * from $tableName WHERE $columnName $operand $criteria Limit $limit"
    }

    fun selectLimit(tableName : String, limit : Long) : String? {
        return "SELECT * from $tableName Limit $limit"
    }

    fun selectAll(tableName : String) : String? {
        return "SELECT * from $tableName"
    }

    fun whereClause(columnName : String, operand : String, criteria : String) : String? {
        return "$columnName $operand '$criteria' "
    }

    fun whereClause(columnName : String, operand : String, criteria : Long) : String? {
        return "$columnName $operand $criteria "
    }

    fun updateWhere(tableName : String, columnInformation : String, columnName : String, operand : String, criteria : String) : String {
        return "UPDATE $tableName SET ${columnInformation} WHERE $columnName $operand $criteria"
    }

    fun updateWhere(tableName : String, setColumn : String, setValue : String, whereColumn : String, operand : String, criteria : String) : String? {
        return "UPDATE $tableName SET $setColumn = '$setValue'  WHERE $whereColumn $operand $criteria"
    }

    fun updateWhere(tableName : String, columnInformation : String, columnName : String, operand : String, criteria : String, limit : Long) : String {
        return "UPDATE $tableName SET ${columnInformation} WHERE $columnName $operand $criteria LIMIT $limit"
    }

    fun delete(tableName : String) : String {
        return "DELETE FROM $tableName"
    }

    fun deleteWhere(tableName : String, columnName : String, operand : String, criteria : String) : String {
        return "DELETE FROM $tableName WHERE $columnName $operand $criteria;"
    }
}