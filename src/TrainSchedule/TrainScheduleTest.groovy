/**
 * Created by DDRDmakar on 2/15/17.
 */
package TrainSchedule


import java.sql.Timestamp

class TrainScheduleTest extends GroovyTestCase {

    // Get current date + some shift in time
    static Timestamp nowTimePlus(double hoursShift = 0) {
        return new Timestamp((System.currentTimeMillis() + 3600*hoursShift).toLong())
        // 3600000 = 1 hour
    }

    void testShedule() {

        // Новый объект "расписание поездов"
        TrainSchedule s = new TrainSchedule()

        // Вывод содержимого - должен быть пустым
        // println(s)

        // Заполнение содержимого
        s.addTrain("Sapsan", nowTimePlus(9), "Брянск")
        s.addIntermediateStation("Sapsan", "Первая")
        s.addIntermediateStation("Sapsan", "Вторая")
        s.addIntermediateStation("Sapsan", "Третья")
        s.addIntermediateStation("Sapsan", "Четвёртая")
        s.addIntermediateStation("Sapsan", "Пятая")
        s.addIntermediateStation("Sapsan", "Шестая")
        s.addIntermediateStation("Sapsan", "Седьмая")
        s.addIntermediateStation("Sapsan", "Одиннадцатая")
        s.addIntermediateStation("Sapsan", "Двенадцатая")
        s.addIntermediateStation("Sapsan", "Тринадцатая")
        s.addIntermediateStation("Sapsan", "Четырнадцатая")

        s.addIntermediateStation("Sapsan", "Восьмая")
        s.addIntermediateStation("Sapsan", "Девятая")
        s.addIntermediateStation("Sapsan", "Десятая")
        s.deleteIntermediateStation("Sapsan", "Восьмая")
        s.deleteIntermediateStation("Sapsan", "Девятая")
        s.deleteIntermediateStation("Sapsan", "Десятая")

        s.addTrain("Allegro", nowTimePlus(2), "Новгород")
        s.addIntermediateStation("Allegro", "Первая")
        s.addIntermediateStation("Allegro", "Третья")
        s.addIntermediateStation("Allegro", "Четвёртая")
        s.addIntermediateStation("Allegro", "Шестая")

        s.addTrain("Fastest", nowTimePlus(0.5), "Новгород")
        s.addIntermediateStation("Fastest", "Первая")
        s.addIntermediateStation("Fastest", "Третья")
        s.addIntermediateStation("Fastest", "Четвёртая")
        s.addIntermediateStation("Fastest", "Шестая")

        s.addTrain("T001", nowTimePlus(-1.5), "Ростов")
        s.addIntermediateStation("T001", "Пятая")
        s.addIntermediateStation("T001", "Шестая")

        s.addTrain("T452", nowTimePlus(4), "Москва")

        s.addTrain("T815", nowTimePlus(7), "Волгоград")
        s.addIntermediateStation("T815", "Вторая")
        s.addIntermediateStation("T815", "Третья")
        s.addIntermediateStation("T815", "Пятая")
        s.addIntermediateStation("T815", "Одиннадцатая")
        s.addIntermediateStation("T815", "Двенадцатая")

        s.deleteTrain("Fastest")


        /*
        s.addTrain("T002", nowTimePlus(-4), "Berlin")
        s.addTrain("T750", nowTimePlus(3), "Киев")
        s.addTrain("T003", nowTimePlus(1.5), "Ростов")
        s.addTrain("D9-000", nowTimePlus(), "Урюпинск")
        */

        // Поиск ближайшего по времени поезда до определённой станции

        // На первую быстрее всего --> Allegro
        // На вторую               --> T815
        // На третью               --> Allegro
        // На четвёртую            --> Allegro
        // На пятую                --> T815
        // На шестую               --> Allegro
        // На седьмую              --> Sapsan
        // На восьмую              --> -
        // На девятую              --> -
        // На десятую              --> -
        // На одиннадцатую         --> T815
        // На двенадцатую          --> T815
        // На тринадцатую          --> Sapsan
        // На четырнадцатую        --> Sapsan

        // Пустая строка означает, что на данную станцию поезда не идут.

        assertEquals("Allegro", s.find_next_train_to("Первая", nowTimePlus()))
        assertEquals("T815", s.find_next_train_to("Вторая", nowTimePlus()))
        assertEquals("Allegro", s.find_next_train_to("Третья", nowTimePlus()))
        assertEquals("Allegro", s.find_next_train_to("Четвёртая", nowTimePlus()))
        assertEquals("T815", s.find_next_train_to("Пятая", nowTimePlus()))
        assertEquals("Allegro", s.find_next_train_to("Шестая", nowTimePlus()))
        assertEquals("Sapsan", s.find_next_train_to("Седьмая", nowTimePlus()))
        assertEquals("", s.find_next_train_to("Восьмая", nowTimePlus()))
        assertEquals("", s.find_next_train_to("Девятая", nowTimePlus()))
        assertEquals("", s.find_next_train_to("Десятая", nowTimePlus()))
        assertEquals("T815", s.find_next_train_to("Одиннадцатая", nowTimePlus()))
        assertEquals("T815", s.find_next_train_to("Двенадцатая", nowTimePlus()))
        assertEquals("Sapsan", s.find_next_train_to("Тринадцатая", nowTimePlus()))
        assertEquals("Sapsan", s.find_next_train_to("Четырнадцатая", nowTimePlus()))

        // println(s)
    }
}
