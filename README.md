# Spring-boot and react chat app
## Многопользовательский чат с возможностью создания приватных и групповых чатов

* [Серверная часть](server/) реализована на Java [Spring](https://spring.io/projects/spring-framework/)
* Взаимодействие с базой данных реализовано с помощью [Hibernate](https://hibernate.org/)
* Реалмзована аутентификация и авторизация пользователей с помощью [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
* Отправка и получение сообщений релизовано с помощью [Spring WebSockets](https://docs.spring.io/spring-framework/reference/web/webflux-websocket.html)
* [Клиентская часть](client/) реализована с помощью [React JS](https://github.com/dawvvlad/spring-boot-react-chat-app/tree/master/client)
* Подключение к WebSocket и подписка на STOMP реализована с помощью [StompJS](https://stomp-js.github.io/)
