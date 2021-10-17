package com.savijan.financetest.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DatabaseTable(tableName = "transactions")
public class MyTransaction {

    @Getter
    @Setter
    @DatabaseField(generatedId = true)
    private long id;

    @Getter
    @Setter
    @DatabaseField
    private String card_name;

    @Getter
    @Setter
    @DatabaseField
    private String name_transaction;

    @Getter
    @Setter
    @DatabaseField
    private String type_transaction;

    @Getter
    @Setter
    @DatabaseField
    private String check_transaction;

    public MyTransaction() {
    }
}
