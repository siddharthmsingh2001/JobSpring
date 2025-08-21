package com.jobspring.jobspring.entity;

public interface Profile {

    Long getAccountId();

    Account getAccount();

    String getFirstName();

    String getLastName();

    String getProfilePhoto();

    String getCity();

    String getState();

    String getCountry();

    default String getAccountTypeName() {
        return getAccount() != null && getAccount().getAccountType() != null
                ? getAccount().getAccountType().getName()
                : null;
    }

}