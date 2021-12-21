package bot;

public class TextMessage {
    public static final String aboutBot = "Хей! Механика моей работы простая\uD83D\uDE09\n " +
            "Ты отправляешь мне ссылки и выбираешь режим чтения\n " +
            "Я - завожу задачи в Todoist, контролирую их выполнение\n " +
            "\n" +
            "В ходе разработки появятся новые фичи и ты обязательно об этом узнаешь ☺️";

    public static final String addTag = "Тег добавлен к задаче!";
    public static final String error = "Ошибка!";
    public static final String cannotUnderstand ="Не понимаю - о чём ты...";
    public static final String completeTask = "Задание выполнено!";
    public static final String createTag = "Создать тег";
    public static final String createdTag = "Тег создан!";
    public static final String createNewShed = "Создать расписание";
    public static final String deletedShed = "Расписание удалено!";
    public static final String deletedTagAsk = "Выбери тег, который ты хочешь удалить:";
    public static final String deleteTag = "Удалить тег";
    public static final String myTags = "Мои теги";
    public static final String deletedTag = "Тег удален!";
    public static final String deletedTask = "Задание удалено!";
    public static final String goodTime = "Отличное время, чтобы изучить что-то новое:\n";
    public static final String havenotTask ="По такому тегу нет задач!";
    public static final String chooseTaskTag = "Выберите тег: ";
    public static final String saveLink ="Сохраняю ссылку...\n" +
            "\n" +
            "Выберите подходящий тег для этой задачи";
    public static final String loginMess = "Для того чтобы залогиниться тебе необходимо отправить мне свой токен точно как в Todoist\n" +
            "Введи его и просто отправь мне⬇️\n" +
            "\n" +
            "Откуда взять токен?\n" +
            "1️⃣ Необходимо нажать на иконку своего профиля в Todoist\n" +
            "2️⃣ Нажать на вкладку  Интеграции \n" +
            "3️⃣ Скопировать Токен API (можно нажать на Скопировать в буфер обмена)\n" +
            "4️⃣ Вернутся в наш чатик и отправить токен мне (вставить)";
    public static final String projectList = "Список твоих проектов: ";
    public static final String readingMode = "Здесь ты можешь работать с расписанием по которому тебе приходят задачи.\n\n" +
            "Ты можешь\n" +
            "➡️Посмотреть все свои расписания по тегам \n" +
            "➡️Создать новое расписание для тега  \n" +
            "➡️Удалить расписание\n";
    public static final String tagsList ="Список твоих тегов: ";
    public static final String mainMess = "Ты мне ссылку, я тебе задачу \n" +
            "Как и договаривались\uD83D\uDE09";
    public static final String shedList ="Список твоих расписаний (для удаления нажми на него):" ;
    public static final String chooseShed ="Выбери тег для которого хочешь посмотрееть расписание \n ⬇️ ️";
    public static final String createShed ="Выбери расписание для тега в формате:\n" +
            "sh 15:45 1,2,3 \n" +
            "где 1 -вс, 2-пн и так далее";
    public static final String wrongTerm = "Неправильное выражение, попробуй еще раз.";
    public static final String goodTerm = "Отлично, расписание сохранено!";
    public static final String startMess ="Привет! Я Todo Bot\uD83E\uDD16\n" +
            "Если ты часто добавляешь видосики, статьи, а может подкасты в закладки," +
            " но у тебя нет времени все это посмотреть, почитать, послушать прямо сейчас, то нам с тобой по пути.";
    public static final String tagInfo = "Добавляя тег к задаче, ты условно делишь свои задачи на категории.\n\n" +
            "Благодаря этому можно быстро получать список задач или конкретную задачу\n" +
            "Это удобно, я проверял\uD83D\uDE09\n\n" +
            "Для того чтобы создать новый тег, введи:" +
            "#название_тега\n" +
            "Например: #читать\n\n" +
            "Ты можешь\n" +
            "➡️Посмотреть все свои теги \n" +
            //"➡️Создать новый тег  \n" +
            "➡️Удалить тег \n";
    public static final String createNewTag = "Для того чтобы создать новый тег, введи:" +
            "#название_тега\n " +
            "Например: #читать\n";
    public static final String tasksList = "Список твоих задач: ";
    public static final String thisIsMe = "Если ты пользователь Todoist, то смело нажимай залогиниться✅\n" +
            "\n" +
            "А если у тебя еще нет профиля, то ссылка внизу\uD83D\uDE09\n" +
            "Скорее регистрируйся и возвращайся ко мне\uD83E\uDD17 \n" +
            "https://todoist.com/users/showregister";
    public static final String timeZone = "Твой часовой пояс - ";
    public static final String goodAuthorization = "Отлично \uD83E\uDD17 Пользователь с таким токеном существует!\n" +
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

    public static final String invalidToken ="Введенный токен не верный, попробуй еще раз";
    public static final String howWorkingBot ="Как работает бот?";
    public static final String chooseTag = "Выбери тег для которого хочешь настроить режим \n ⬇️ ️";
    public static final String giveTask = "Дай Задачу";
    public static final String giveTaskByTag = "Дай задачу по тегу";
    public static final String logging = "Залогиниться✅";
    public static final String myProject = "Мои проекты";
    public static final String projects ="Проекты";
    public static final String projectsInfo = "Ты можешь\n" +
            "➡️ Создать новый проект\n" +
            "➡️ Посмотреть список всех проектов";
    public static final String shedule = "Расписание";
    public static final String back ="Назад \uD83D\uDD19";
    public static final String myShedule = "Моё расписание";
    public static final String tags = "Теги";
    public static final String taskByTag = "Задачи по тегу";
    public static final String tasks ="Задачи";
    public static final String isMe = "Точно я!";
    public static final String applyTimeZone = "Задать часовой пояс";





}
