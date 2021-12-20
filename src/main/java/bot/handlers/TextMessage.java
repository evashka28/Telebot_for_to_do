package bot.handlers;

public class TextMessage {
    public static final String about_bot = "Хей! Механика моей работы простая\uD83D\uDE09\n " +
            "Ты отправляешь мне ссылки и выбираешь режим чтения\n " +
            "Я - завожу задачи в Todoist, контролирую их выполнение\n " +
            "\n" +
            "В ходе разработки появятся новые фичи и ты обязательно об этом узнаешь ☺️";

    public static final String add_tag = "Тег добавлен к задаче!";
    public static final String error = "Ошибка!";
    public static final String cannot_understand ="Не понимаю - о чём ты...";
    public static final String complete_task = "Задание выполнено!";
    public static final String create_tag = "Создать тег";
    public static final String created_tag = "Тег создан!";
    public static final String create_new_shed = "Создать расписание";
    public static final String deleted_shed = "Расписание удалено!";
    public static final String deleted_tag_ask = "Выбери тег, который ты хочешь удалить:";
    public static final String delete_tag = "Удалить тег";
    public static final String my_tags = "Мои теги";
    public static final String deleted_tag = "Тег удален!";
    public static final String deleted_task = "Задание удалено!";
    public static final String good_time = "Отличное время, чтобы изучить что-то новое:\n";
    public static final String havenot_task ="По такому тегу нет задач!";
    public static final String choose_task_tag = "Выберите тег: ";
    public static final String save_link ="Сохраняю ссылку...\n" +
            "\n" +
            "Выберите подходящий тег для этой задачи";
    public static final String login_mess = "Для того чтобы залогиниться тебе необходимо отправить мне свой токен точно как в Todoist\n" +
            "Введи его и просто отправь мне⬇️\n" +
            "\n" +
            "Откуда взять токен?\n" +
            "1️⃣ Необходимо нажать на иконку своего профиля в Todoist\n" +
            "2️⃣ Нажать на вкладку  Интеграции \n" +
            "3️⃣ Скопировать Токен API (можно нажать на Скопировать в буфер обмена)\n" +
            "4️⃣ Вернутся в наш чатик и отправить токен мне (вставить)";
    public static final String project_list = "Список твоих проектов: ";
    public static final String reading_mode = "Здесь ты можешь работать с расписанием по которому тебе приходят задачи.\n\n" +
            "Ты можешь\n" +
            "➡️Посмотреть все свои расписания по тегам \n" +
            "➡️Создать новое расписание для тега  \n" +
            "➡️Удалить расписание\n";
    public static final String tags_list ="Список твоих тегов: ";
    public static final String main_mess = "Ты мне ссылку, я тебе задачу \n" +
            "Как и договаривались\uD83D\uDE09";
    public static final String shed_list ="Список твоих расписаний (для удаления нажми на него):" ;
    public static final String choose_shed ="Выбери тег для которого хочешь посмотрееть расписание \n ⬇️ ️";
    public static final String create_shed ="Выбери расписание для тега в формате:\n" +
            "sh 15:45 1,2,3 \n" +
            "где 1 -вс, 2-пн и так далее";
    public static final String wrong_term = "Неправильное выражение, попробуй еще раз.";
    public static final String good_term = "Отлично, расписание сохранено!";
    public static final String start_mess ="Привет! Я Todo Bot\uD83E\uDD16\n" +
            "Если ты часто добавляешь видосики, статьи, а может подкасты в закладки," +
            " но у тебя нет времени все это посмотреть, почитать, послушать прямо сейчас, то нам с тобой по пути.";
    public static final String tag_info = "Добавляя тег к задаче, ты условно делишь свои задачи на категории.\n\n" +
            "Благодаря этому можно быстро получать список задач или конкретную задачу\n" +
            "Это удобно, я проверял\uD83D\uDE09\n\n" +
            "Ты можешь\n" +
            "➡️Посмотреть все свои теги \n" +
            "➡️Создать новый тег  \n" +
            "➡️Удалить тег \n";
    public static final String create_new_tag = "Для того чтобы создать новый тег, введи:" +
            "#название_тега\n " +
            "Например: #читать\n";
    public static final String tasks_list = "Список твоих задач: ";
    public static final String this_is_me = "Если ты пользователь Todoist, то смело нажимай залогиниться✅\n" +
            "\n" +
            "А если у тебя еще нет профиля, то ссылка внизу\uD83D\uDE09\n" +
            "Скорее регистрируйся и возвращайся ко мне\uD83E\uDD17 \n" +
            "https://todoist.com/users/showregister";
    public static final String time_zone = "Твой часовой пояс - ";
    public static final String good_authorization = "Отлично \uD83E\uDD17 Пользователь с таким токеном существует!\n" +
            "\n" +
            "В твоём профиле Todoist я специально создал проект с названием fromToDoBot.\n" +
            "Именно туда я буду добавлять все ссылки, которые ты мне пришлёшь \uD83D\uDE09\n" +
            "\n" +
            "Немного о моих кнопках:\n" +
            "\uD83D\uDCCDРасписание \n" +
            "Ты можешь создавать расписание, а я буду отправлять тебе ссылку в настроенное время\n" +
            "\uD83D\uDCCDДай задачу\n" +
            "Дам одну любую ссылку из имеющихся\n" +
            "\uD83D\uDCCDТеги\n" +
            "можно создать теги и бытро находить задачи\n" +
            "\uD83D\uDCCDЗадачи\n" +
            "Дам все ссылки, которые ты мне присылал\n" +
            "\uD83D\uDCCDДай Задачу по тегу\n" +
            "Отыщу любую задачу по выбранному тегу\n" +
            "\uD83D\uDCCDЗадачи по тегу\n" +
            "Верну все задачи по выбранному тегу\n" +
            "\uD83D\uDCCDЗадать часовой пояс\n" +
            "Необходимо настроить для корректной работы расписания\n" +
            "\uD83D\uDCCDКак работает бот?\n" +
            "Краткая информация обо мне";

    public static final String invalid_token ="Введенный токен не верный, попробуй еще раз";
    public static final String how_working_bot ="Как работает бот?";
    public static final String choose_tag = "Выбери тег для которого хочешь настроить режим \n ⬇️ ️";
    public static final String give_task = "Дай Задачу";
    public static final String give_task_by_tag = "Дай задачу по тегу";
    public static final String logging = "Залогиниться✅";
    public static final String my_project = "Мои проекты";
    public static final String projects ="Проекты";
    public static final String projects_info = "Ты можешь\n" +
            "➡️ Создать новый проект\n" +
            "➡️ Посмотреть список всех проектов";
    public static final String shedule = "Расписание";
    public static final String back ="Назад \uD83D\uDD19";
    public static final String my_shedule = "Моё расписание";
    public static final String tags = "Теги";
    public static final String task_by_tag = "Задачи по тегу";
    public static final String tasks ="Задачи";
    public static final String is_me = "Точно я!";




}
