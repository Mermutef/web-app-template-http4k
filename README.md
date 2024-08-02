# Prom-template

## База данных

Развертывать базу данных лучше в docker-контейнере

Для этого необходим сам
докер ([Windows](https://yandex.ru/q/machine-learning/11403789570/), [Linux](https://docs.docker.com/desktop/install/linux-install/))

```bash
docker run -d --name promdb -e POSTGRES_PASSWORD=password -p 5432:5432 postgres:16.3
```

### После этого требуется создать базу данных

```bash
docker exec -it promdb bash
```

```chatinput
psql -h localhost -U postgres
```

```chatinput
CREATE DATABASE prom;
```
### Для резервного копирования и восстановления
```bash
docker exec -it -u postgres promdb bash
```
#### Копирование
```chatinput
pg_dump -f /var/lib/postgresql/data/prom_dump_ENTER-THE-CURRENT-DATE!!!.sql -U postgres prom
```
#### Восстановление
```chatinput
psql -h localhost -U postgres
```
```chatinput
psql -d promdb -U postgres -f ENTER-THE-DUMP-FILENAME.sql
```

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
