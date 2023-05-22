package ru.zaza.multitaskbot.commands;

public interface Command<T> {

    void execute(T t);

}
