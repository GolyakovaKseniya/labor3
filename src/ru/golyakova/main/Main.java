package ru.golyakova.main;

import ru.golyakova.birds.Bird;
import ru.golyakova.birds.Cuckoo;
import ru.golyakova.birds.Parrot;
import ru.golyakova.birds.Sparrow;
import ru.golyakova.city.*;
import ru.golyakova.geometry.Point3D;
import ru.golyakova.math.PowerCalculator;
import ru.golyakova.validate.Validator;
import ru.golyakova.exceptions.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, City> cities = new HashMap<>();
        Map<String, CityInterface> cities1 = new HashMap<>();
        boolean flag1 = false;
        while (!flag1) {
            System.out.println("Выберите действие: ");
            System.out.println("Задача 1.10");
            System.out.println("Задача 3.3");
            System.out.println("Задача 3.5");
            System.out.println("Задача 4.3");
            System.out.println("Задача 5.9");
            System.out.println("Задача 7.3");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1.10": {
                    boolean flag = false;
                    while (!flag) {
                        try {
                            System.out.println("1 - Создать город");
                            System.out.println("2 - Создать город с заранее созданными путями");
                            System.out.println("3 - Добавить дорогу");
                            System.out.println("4 - Удалить дорогу");
                            System.out.println("5 - Показать все города");
                            System.out.println("0 - Выход");
                            System.out.print("Выберите действие: ");
                            String choice = scanner.nextLine();
                            switch (choice) {
                                //создание города
                                case "1": {
                                    System.out.print("Введите название города: ");
                                    String name = scanner.nextLine();
                                    City city = Validator.createCity(name, cities);
                                    System.out.println("Город " + city.getName() + " создан");
                                    System.out.println();
                                    break;
                                }
                                case "2":{
                                    System.out.print("Введите название города: ");
                                    String name = scanner.nextLine();
                                    Map<String,Integer> roads = new HashMap<>();
                                    System.out.print("Сколько ввести дорог для города: ");
                                    String roadCountInput = scanner.nextLine();
                                    int roadCount = Validator.validPositiveNumber(roadCountInput);
                                    for(int i = 1; i<=roadCount; i++){
                                        System.out.println("Добавление дороги: " + i + " из " + roadCount + " : ");
                                        System.out.print("Введите город назначения: ");
                                        String toCity = scanner.nextLine();
                                        System.out.print("Введите стоимость: ");
                                        String costInput = scanner.nextLine();
                                        int cost = Validator.validPositiveNumber(costInput);
                                        roads.put(toCity, cost);
                                        System.out.println("Дорога до '" + toCity + "' добавлена!");
                                    }
                                    City newCity;
                                    if (!roads.isEmpty()) {
                                        newCity = Validator.createCityWithRoad(name, roads, cities);
                                        System.out.println("Город '" + newCity.getName() + "' создан с " + roads.size() + " дорогами!");
                                    } else {
                                        newCity = Validator.createCity(name, cities);
                                        System.out.println("Город '" + newCity.getName() + "' создан без дорог!");
                                    }
                                    System.out.println();
                                    break;
                                }
                                //добавление дороги
                                case "3": {
                                    Validator.validEnoughCity(cities.size(), 2, "добавления дороги");
                                    String name1 = Validator.readAndValidCityName(scanner, "Введите первый город: ");
                                    String name2 = Validator.readAndValidCityName(scanner, "Введите второй город: ");
                                    Validator.validDifferentCity(name1, name2);
                                    City city1 = Validator.getValidCity(name1, cities);
                                    City city2 = Validator.getValidCity(name2, cities);
                                    System.out.print("Введите стоимость: ");
                                    String costInput = scanner.nextLine();
                                    int cost = Validator.validPositiveNumber(costInput);
                                    city1.addRoad(city2, cost);
                                    System.out.println("Дорога между " + name1 + " и " + name2 + " стоимостью " + cost + " добавлена");
                                    System.out.println();
                                    break;
                                }
                                //удаление дороги
                                case "4": {
                                    Validator.validEnoughCity(cities.size(), 1, "удаления дороги");
                                    String name1 = Validator.readAndValidCityName(scanner, "Введите первый город: ");
                                    String name2 = Validator.readAndValidCityName(scanner, "Введите второй город: ");
                                    City city1 = Validator.getValidCity(name1, cities);
                                    City city2 = Validator.getValidCity(name2, cities);
                                    city1.removeRoad(city2);
                                    System.out.println("Дорога между " + name1 + " и " + name2 + " удалена");
                                    System.out.println();
                                    break;
                                }
                                //все города
                                case "5": {
                                    Validator.validEnoughCity(cities.size(), 1, "просмотра городов");
                                    for (City city : cities.values()) {
                                        System.out.print("Город " + city.getName() + " : ");
                                        Map<String, Integer> roads = city.getRoads();
                                        if (roads.isEmpty()) {
                                            System.out.println("Дорог нет ");
                                        } else {
                                            boolean first = true;
                                            for (Map.Entry<String, Integer> entry : roads.entrySet()) {
                                                if (!first) {
                                                    System.out.print(", ");
                                                }
                                                System.out.print(entry.getKey() + "{" + entry.getValue() + "}");
                                                first = false;
                                            }
                                            System.out.println();
                                        }
                                    }
                                    System.out.println();
                                    break;
                                }
                                case "0": {
                                    System.out.println("Выход");
                                    System.out.println();
                                    flag = true;
                                    break;
                                }
                                default: {
                                    throw new InvalidInputException("Неверный выбор");
                                }
                            }
                        } catch (RoadSystemException e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    }
                    break;
                }
                case "3.3":{
                    boolean flag = false;
                    while (!flag) {
                        try {
                            System.out.println("1 - Создать город");
                            System.out.println("2 - Создать город с заранее созданными путями");
                            System.out.println("3 - Добавить дорогу");
                            System.out.println("4 - Удалить дорогу");
                            System.out.println("5 - Показать все города");
                            System.out.println("0 - Выход");
                            System.out.print("Выберите действие: ");
                            String choice = scanner.nextLine();
                            switch (choice) {
                                case "1":{
                                    try {
                                        System.out.print("Введите название города: ");
                                        String name = scanner.nextLine();
                                        BidirectionalCity city = Validator.createTwoWayCity(name,cities);
                                        System.out.println("Город " + city.getName() + " создан");
                                    }catch (RoadSystemException e){
                                        System.out.println("Ошибка: " + e.getMessage());
                                    }
                                    System.out.println();
                                    break;
                                }
                                case "2":{
                                    try {
                                        System.out.print("Введите название города: ");
                                        String name = scanner.nextLine();
                                        Map<String,Integer> roads = new HashMap<>();
                                        System.out.print("Сколько ввести дорог для города: ");
                                        String roadCountInput = scanner.nextLine();
                                        int roadCount = Validator.validPositiveNumber(roadCountInput);
                                        for(int i = 1; i<=roadCount; i++){
                                            System.out.println("Добавление дороги: " + i + " из " + roadCount + " : ");
                                            System.out.print("Введите город назначения: ");
                                            String toCity = scanner.nextLine();
                                            System.out.print("Введите стоимость: ");
                                            String costInput = scanner.nextLine();
                                            int cost = Validator.validPositiveNumber(costInput);
                                            roads.put(toCity, cost);
                                            System.out.println("Дорога до '" + toCity + "' добавлена!");
                                        }
                                        BidirectionalCity newCity = Validator.createTwoWayCityWithRoads(name,roads,cities);
                                        System.out.println("Двусторонний город '" + newCity.getName() + "' создан с " + roads.size() + " дорогами!");

                                    }catch (RoadSystemException e){
                                        System.out.println("Ошибка: " + e.getMessage());
                                    }
                                    break;
                                }
                                case "3":{
                                    try {
                                        Validator.validEnoughCity(cities.size(), 2, "добавления дороги");
                                        String name1 = Validator.readAndValidCityName(scanner, "Введите первый город: ");
                                        String name2 = Validator.readAndValidCityName(scanner, "Введите второй город: ");
                                        Validator.validDifferentCity(name1, name2);
                                        City city1 = Validator.getValidCity(name1, cities);
                                        City city2 = Validator.getValidCity(name2,cities);
                                        System.out.print("Введите стоимость: ");
                                        String costInput = scanner.nextLine();
                                        int cost = Validator.validPositiveNumber(costInput);
                                        city1.addRoad(city2, cost);
                                        System.out.println("Двусторонняя дорога между " + name1 + " и " + name2 + " стоимостью " + cost + " создана");
                                    }catch (RoadSystemException e) {
                                        System.out.println("Ошибка: " + e.getMessage());
                                    }
                                    break;
                                }
                                case "4":{
                                    try {
                                        Validator.validEnoughCity(cities.size(), 1, "удаления дороги");
                                        String name1 = Validator.readAndValidCityName(scanner, "Введите первый город: ");
                                        String name2 = Validator.readAndValidCityName(scanner, "Введите второй город: ");
                                        City city1 = Validator.getValidCity(name1, cities);
                                        City city2 = Validator.getValidCity(name2,cities);
                                        city1.removeRoad(city2);
                                        System.out.println("Двусторонняя дорога между " + name1 + " и " + name2 + " удалена");
                                    }catch (RoadSystemException e) {
                                        System.out.println("Ошибка: " + e.getMessage());
                                    }
                                    break;
                                }
                                case "5":{
                                    Validator.validEnoughCity(cities.size(), 1, "просмотра городов");
                                    for (City city : cities.values()) {
                                        System.out.print("Город " + city.getName() + " : ");
                                        Map<String, Integer> roads = city.getRoads();
                                        if (roads.isEmpty()) {
                                            System.out.println("Дорог нет ");
                                        } else {
                                            boolean first = true;
                                            for (Map.Entry<String, Integer> entry : roads.entrySet()) {
                                                if (!first) {
                                                    System.out.print(", ");
                                                }
                                                System.out.print(entry.getKey() + "{" + entry.getValue() + "}");
                                                first = false;
                                            }
                                            System.out.println();
                                        }
                                    }
                                    System.out.println();
                                    break;
                                }
                                case "0":{
                                    System.out.println("Выход");
                                    System.out.println();
                                    flag = true;
                                    break;
                                }
                                default:{
                                    throw new InvalidInputException("Неверный выбор");
                                }
                            }
                        }catch (RoadSystemException e){
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    }
                    break;
                }
                case "3.5":{
                    Point3D[] point = new Point3D[3];
                    for(int i = 0; i<3; i++){
                        System.out.println("Точка "+ (i+1) + ": ");
                        double x = Validator.readDouble(scanner,"Введите координату Х: ");
                        double y = Validator.readDouble(scanner,"Введите координату Y: ");
                        double z = Validator.readDouble(scanner,"Введите координату Z: ");
                        point[i] = new Point3D(x, y, z);
                        System.out.println("Точка создана " + point[i]);
                    }
                    System.out.println("Созданные точки: ");
                    for (int i = 0; i<point.length; i++){
                        System.out.println("Точка " + (i+1) +": " + point[i]);
                    }
                    System.out.println();
                    if (scanner.hasNextLine()) {
                        scanner.nextLine();
                    }
                    break;
                }
                case "4.3":{
                    List<Bird> birds = new ArrayList<>();
                    boolean flag = false;
                    while (!flag) {
                        try {
                            System.out.println("1 - Добавить воробья");
                            System.out.println("2 - Добавить кукушку");
                            System.out.println("3 - Добавить попугая");
                            System.out.println("4 - Показать всех птиц");
                            System.out.println("5 - Послушать пение всех птиц");
                            System.out.println("6 - Послушать конкретную птицу");
                            System.out.println("0 - Выход");
                            System.out.print("Ваш выбор: ");
                            String choice = scanner.nextLine();
                            switch (choice) {
                                case "1": {
                                    Bird sparrow = new Sparrow();
                                    birds.add(sparrow);
                                    System.out.println("Воробей добавлен");
                                    System.out.println();
                                    break;
                                }
                                case "2": {
                                    Bird cuckoo = new Cuckoo();
                                    birds.add(cuckoo);
                                    System.out.println("Кукушка добавлена");
                                    System.out.println();
                                    break;
                                }
                                case "3": {
                                    try {
                                        System.out.print("Введите текст для попугая: ");
                                        String text = scanner.nextLine();
                                        Validator.validCityName(text);
                                        Bird parrot = new Parrot(text);
                                        birds.add(parrot);
                                        System.out.println("Попугай с текстом '" + text + "' добавлен");
                                    } catch (Exception e) {
                                        System.out.println("Ошибка " + e.getMessage());
                                    }
                                    System.out.println();
                                    break;
                                }
                                case "4": {
                                    if (birds.isEmpty()) {
                                        System.out.println("Птиц нет");
                                    }
                                    for (int i = 0; i < birds.size(); i++) {
                                        System.out.print((i + 1));
                                        birds.get(i).display();
                                    }
                                    System.out.println();
                                    break;
                                }
                                case "5": {
                                    if (birds.isEmpty()) {
                                        System.out.println("Птиц нет");
                                    }
                                    for (Bird bird : birds) {
                                        System.out.print(bird.getName() + " поет:  ");
                                        bird.sing();
                                    }
                                    System.out.println();
                                    break;
                                }
                                case "6": {
                                    if (birds.isEmpty()) {
                                        System.out.println("Птиц нет");
                                    }
                                    for (int i = 0; i < birds.size(); i++) {
                                        System.out.print((i + 1));
                                        birds.get(i).display();
                                    }
                                    System.out.print("Выберите номер птицы: ");
                                    try {
                                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                                        if (index >= 0 && index < birds.size()) {
                                            System.out.print(birds.get(index).getName() + " поет: ");
                                            birds.get(index).sing();
                                        } else {
                                            System.out.println("Ошибка в номере птицы");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Ошибка в номере");
                                    }
                                    System.out.println();
                                    break;
                                }
                                case "0": {
                                    System.out.println("Выход");
                                    System.out.println();
                                    flag = true;
                                    break;
                                }
                                default: {
                                    throw new InvalidInputException("Неверный выбор");
                                }
                            }
                        }catch (Exception e){
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    }
                    break;
                }
                case "5.9":{
                    boolean flag = false;
                    while (!flag) {
                        try {
                            System.out.println("1 - Создать город");
                            System.out.println("2 - Добавить дорогу");
                            System.out.println("3 - Показать все города");
                            System.out.println("0 - Выход");
                            System.out.print("Выберите действие: ");
                            String choice = scanner.nextLine();
                            switch (choice) {
                                case"1":{
                                    try {
                                        System.out.print("Введите название города: ");
                                        String name = scanner.nextLine().trim();
                                        CityInterface city = Validator.createCity(name, false);
                                        System.out.println("Город '" + city.getName() + "' создан");
                                        cities1.put(name,city);
                                    }catch (Exception e){
                                        System.out.println("Ошибка " + e.getMessage());
                                    }
                                    break;
                                }
                                case "2":{
                                    try {
                                        Validator.validEnoughCity(cities1.size(), 2, "добавления дороги");
                                        System.out.print("Введите город отправления: ");
                                        String from = scanner.nextLine().trim();
                                        System.out.print("Введите город назначения: ");
                                        String to = scanner.nextLine().trim();

                                        CityInterface fromCity = cities1.get(from);
                                        CityInterface toCity = cities1.get(to);

                                        if(fromCity.hasRoadTo(toCity.getName())){
                                            System.out.println("Дорога из " + fromCity.getName() + " в " + toCity.getName() + " уже существует!");
                                            break;
                                        }
                                        System.out.print("Введите стоимость дороги: ");
                                        String costInput = scanner.nextLine();
                                        int cost = Validator.validPositiveNumber(costInput);
                                        System.out.println("Выберите тип дороги:");
                                        System.out.println("1 - Односторонняя дорога");
                                        System.out.println("2 - Двусторонняя дорога");
                                        System.out.print("Ваш выбор: ");
                                        String typeChoice = scanner.nextLine();
                                        if("2".equals(typeChoice)){
                                            fromCity.addRoad(toCity, cost);
                                            toCity.addRoad(fromCity,cost);
                                            System.out.println("Двусторонняя дорога " + fromCity.getName() +" <> " + toCity.getName() + " создана");
                                        }else{
                                            fromCity.addRoad(toCity,cost);
                                            System.out.println("Обычная дорога " + fromCity.getName() + " <> " + toCity.getName() + " создана");
                                        }
                                    }catch (RoadSystemException e){
                                        System.out.println("Ошибка: " + e.getMessage());
                                    } catch (NumberFormatException e){
                                        System.out.println("Ошибка: введите корректное число для стоимости");
                                    }
                                    break;
                                }
                                case "3":{
                                    for(CityInterface city : cities1.values()){
                                        System.out.print(city.getName() + " → ");
                                        Map<String, Integer> roads = city.getRoads();
                                        if(roads.isEmpty()){
                                            System.out.println("нет дорог");
                                        }else{
                                            List<String> ways = new ArrayList<>();
                                            for(Map.Entry<String,Integer> entry : roads.entrySet()){
                                                ways.add(entry.getKey() + "(" + entry.getValue() + ")");
                                            }
                                            System.out.println(String.join(", ", ways));
                                        }
                                    }
                                    break;
                                }
                                case"0":{
                                    System.out.println("Выход");
                                    System.out.println();
                                    flag = true;
                                    break;
                                }
                                default:{
                                    throw new InvalidInputException("Неверный выбор");
                                }
                            }
                        }catch (Exception e){
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    }
                }
                case "7.3":{
                    if(args.length >= 2){
                        calculatePowerFromArgs(args);
                    }
                }
                case "0":{
                    System.out.println("Выход");
                    return;
                }
                default:{
                    System.out.println("Неверный выбор");
                    break;
                }
            }
        }
    }
    private static void calculatePowerFromArgs(String[] args){
        try {
            double result = PowerCalculator.calculatePower(args[0], args[1]);
            System.out.println(args[0] + " ^ " + args[1] + " = " + result);
        }catch (IllegalArgumentException e){
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}