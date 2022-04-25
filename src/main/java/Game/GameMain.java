package Game;

public class GameMain {


    private static int x=-1,y=0, direction=1, length=3;

    private static boolean have_to_decrease = true;

    public static void main(String[] args) {
        GUI.init();

        ///Создаём ягодку в случайном месте
        generate_new_obj();

        ///Пока не получим сигнал на закрытие, в цикле...
        while(!isExitRequested){
            ///Проверяем ввод данных
            input();

            ///Двигаем змею
            move();

            ///Обновляем и рисуем графические элементы
            GUI.draw();
            GUI.update(have_to_decrease);

        }
    }

    private static void move() {
        /// Если на прошлом тике мы что-то съели, то на этом должны вернуть значение на true
        have_to_decrease=true;

        ///Меняем координаты змеи в зависимости от направления
        switch(direction){
            case 0:
                y++; break;
            case 1:
                x++; break;
            case 2:
                y--; break;
            case 3:
                x--; break;
        }

        ///Проверяем, не вышла ли змея за границы
        if(x < 0 || x >= CELLS_COUNT_X || y < 0 || y >= CELLS_COUNT_Y){
            //TODO gameover
            System.exit(1);
        }

        ///Смотрим состояние ячейки, куда зашла змея
        int next_cell_state = GUI.getState(x,y);

        ///Если там змея, то это проигрыш
        if(next_cell_state>0){
            //TODO gameover
            System.exit(1);

        }else{
            ///Если там еда, то
            if(next_cell_state < 0){
                length++; ///Увеличиваем длину на единицу
                generate_new_obj(); ///Создаём новую еду
                have_to_decrease=false; ///Выставляем флаг того, что мы съели что-то
            }

            ///"Зажигаем" клетку
            GUI.setState(x,y,length);
        }
    }


    private static void generate_new_obj() {
        int point = new Random().nextInt(CELLS_COUNT_X*CELLS_COUNT_Y-length);

        for(int i=0; i<CELLS_COUNT_X; i++){
            for(int j=0; j<CELLS_COUNT_Y; j++){
                if(GUI.getState(i,j) <= 0) {
                    if (point == 0) {
                        GUI.setState(i, j, -1); //TODO randomize objects
                        return;
                    }else {
                        point--;
                    }
                }
            }
        }

    }


    private static void input(){
        ///Перебираем события клавиатуры

        int newdirection = direction;

        while(Keyboard.next()){
            if(Keyboard.getEventKeyState()){
                switch(Keyboard.getEventKey()) {

                    case Keyboard.KEY_ESCAPE:
                        isExitRequested = true;
                        break;
                    case Keyboard.KEY_UP:
                        if(direction!=2) newdirection=0;
                        break;
                    case Keyboard.KEY_RIGHT:
                        if(direction!=3) newdirection=1;
                        break;
                    case Keyboard.KEY_DOWN:
                        if(direction!=0) newdirection=2;
                        break;
                    case Keyboard.KEY_LEFT:
                        if(direction!=1) newdirection=3;
                        break;
                }
            }
        }

        direction = newdirection; //Эти рокировки нужны, чтобы правильно работала система условий,
        //запрещающая поворачивать назад, "в себя"

        ///Обрабатываем клик по кнопке "закрыть" окна
        isExitRequested=isExitRequested || Display.isCloseRequested();
    }

}