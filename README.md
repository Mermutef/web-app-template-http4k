# Prom-template

## БД

Развертывать базу данных лучше в docker-контейнере
Для этого необходим сам
докер ([Windows](https://yandex.ru/q/machine-learning/11403789570/), [Linux](https://docs.docker.com/desktop/install/linux-install/))

```bash
docker run -d --name promdb -e POSTGRES_PASSWORD=password -p 5432:5432 postgres:16.3
```

После этого требуется создать базу данных

```bash
docker exec -it promdb bash
```

```chatinput
psql -h localhost -U postgres
```

```chatinput
CREATE DATABASE prom;
```

## Директории

В директории _json_ лежит база на 1700+ объявлений, она является основной

В директории _small-json_ лежит база с 3 пользователями: админ, модер и специалист

Журналы записываются в директорию _logs_ в корне проекта, директория создается автоматически при старте (если ее до
этого не было)

## Аккаунты ролей

### Администратор:

- __имя пользователя:__ admin
- __пароль:__ admin

### Модератор:

- __имя пользователя:__ moderator
- __пароль:__ moderator

### Специалист:

- __имя пользователя:__ specialist
- __пароль:__ specialist