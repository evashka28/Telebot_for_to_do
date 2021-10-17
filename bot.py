#Загуржаем модули библиотеки aiogram и токен бота, а так же инициализируем объекты бота и диспетчера
from aiogram import Bot, types
from aiogram.dispatcher import Dispatcher
from aiogram.utils import executor

from config import TOKEN


bot = Bot(token=TOKEN)
dp = Dispatcher(bot)

#Создаем message_handler для /start и объявляем там функцию ответа

@dp.message_handler(commands=['start'])
async def process_start_command(message: types.Message):
    await message.reply("Привет!\nНапиши мне что-нибудь!")

#Создаем message_handler для /help и объявляем там функцию ответа
@dp.message_handler(commands=['help'])
async def process_help_command(message: types.Message):
    await message.reply("Напиши мне что-нибудь, и я отпрпавлю этот текст тебе в ответ!")

#Делаем обработку текстового сообщения(Если не указывать тип обрабатываемого сообщения, 
#то библиотека по умолчанию делает обработку только текстовых сообщений)

@dp.message_handler()
async def echo_message(msg: types.Message):
    await bot.send_message(msg.from_user.id, msg.text)

#Делаем постоянный опрос сервера на наличие новых обновлений

if __name__ == '__main__':
    executor.start_polling(dp)