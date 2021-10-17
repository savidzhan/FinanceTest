package com.savijan.financetest.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DatabaseTable(tableName = "item_card")
public class MyCard {

    @Getter
    @Setter
    @DatabaseField(generatedId = true)
    private long id;

    @Getter
    @Setter
    @DatabaseField
    private String name;

    @Getter
    @Setter
    @DatabaseField
    private String cash;

    public MyCard() {
    }
}


