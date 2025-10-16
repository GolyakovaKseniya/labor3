package ru.golyakova.exceptions;

public class InvalidCityException extends RoadSystemException{
    public InvalidCityException(String message){
        super(message);
    }
}
