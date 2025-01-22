package com.example.trinhnghenhac.constants;

import androidx.annotation.StringDef;

@StringDef({
        FirebaseCollection.COLLECTION_SAVED,
        FirebaseCollection.COLLECTION_STARRED,
        FirebaseCollection.COLLECTION_ACCOUNTS
})
public @interface FirebaseCollection {
    String COLLECTION_SAVED = "saved";
    String COLLECTION_STARRED = "starred";
    String COLLECTION_ACCOUNTS = "accounts";
}
