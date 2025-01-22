package com.example.trinhnghenhac.constants;

import androidx.annotation.StringDef;

@StringDef({
        FirebaseField.FIELD_SAVED_ACCOUNTID,
        FirebaseField.FIELD_SAVED_ITEMID,
        FirebaseField.FIELD_SAVED_PLATFORM,
        FirebaseField.FIELD_SAVED_TYPE,
        FirebaseField.FIELD_ACCOUNTS_ACCOUNT,
        FirebaseField.FIELD_ACCOUNTS_BIRTH,
        FirebaseField.FIELD_ACCOUNTS_EMAIL,
        FirebaseField.FIELD_ACCOUNTS_ID,
        FirebaseField.FIELD_ACCOUNTS_NAME,
        FirebaseField.FIELD_ACCOUNTS_PASSWORD,
        FirebaseField.FIELD_ACCOUNTS_PHONE
})
public @interface FirebaseField {
    String FIELD_SAVED_ACCOUNTID = "accountId";
    String FIELD_SAVED_ITEMID = "itemId";
    String FIELD_SAVED_PLATFORM = "platform";
    String FIELD_SAVED_TYPE = "type";
    String FIELD_ACCOUNTS_ACCOUNT = "account";
    String FIELD_ACCOUNTS_BIRTH = "birth";
    String FIELD_ACCOUNTS_EMAIL = "email";
    String FIELD_ACCOUNTS_ID = "id";
    String FIELD_ACCOUNTS_NAME = "name";
    String FIELD_ACCOUNTS_PASSWORD = "password";
    String FIELD_ACCOUNTS_PHONE = "phone";
}
