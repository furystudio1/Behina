package com.fury.behina;

class Bean2 {
    String initial, firstName, middleName, lastName, today;

    Bean2(String initial, String firstName, String middleName, String lastName,String today) {
        this.initial = initial;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.today = today;
    }

    public String getInitial() {
        return initial;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getToday() {
        return today;
    }

}
