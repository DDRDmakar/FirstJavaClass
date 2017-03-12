package TrainSchedule

import junit.framework.Test

import java.sql.Timestamp

class TrainScheduleTest extends GroovyTestCase {

    // Get current date + some shift in time
    private static Timestamp nowTimePlus(double hoursShift = 0) {
        return new Timestamp((System.currentTimeMillis() + 3600*hoursShift).toLong())
        // 3600000 = 1 hour
    }

    @org.junit.Test
    void testShedule() {
        // Новый объект "расписание поездов"
        TrainSchedule s = new TrainSchedule()

        // Вывод содержимого - должен быть пустым

        // Заполнение содержимого
        // addTrain Аргументы: Название поезда, время прибытия, конечная станция
        // addIntermediateStation Аргументы: Название поезда, название промежуточной станции

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

        // findNextTrainTo возвращает имя ближайшего по времени поезда на данную станцию
        // Аргументы: Имя станци, текущее время

        assertEquals("Allegro", s.findNextTrainTo("Первая", nowTimePlus()))
        assertEquals("T815", s.findNextTrainTo("Вторая", nowTimePlus()))
        assertEquals("Allegro", s.findNextTrainTo("Третья", nowTimePlus()))
        assertEquals("Allegro", s.findNextTrainTo("Четвёртая", nowTimePlus()))
        assertEquals("T815", s.findNextTrainTo("Пятая", nowTimePlus()))
        assertEquals("Allegro", s.findNextTrainTo("Шестая", nowTimePlus()))
        assertEquals("Sapsan", s.findNextTrainTo("Седьмая", nowTimePlus()))

        assertEquals("", s.findNextTrainTo("Восьмая", nowTimePlus()))
        assertEquals("", s.findNextTrainTo("Девятая", nowTimePlus()))
        assertEquals("", s.findNextTrainTo("Десятая", nowTimePlus()))

        assertEquals("T815", s.findNextTrainTo("Одиннадцатая", nowTimePlus()))
        assertEquals("T815", s.findNextTrainTo("Двенадцатая", nowTimePlus()))
        assertEquals("Sapsan", s.findNextTrainTo("Тринадцатая", nowTimePlus()))
        assertEquals("Sapsan", s.findNextTrainTo("Четырнадцатая", nowTimePlus()))
    }
}
