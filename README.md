# Server-Client

Program sieciowy

Napisz program sieciowy (aplikacja kliencka + aplikacja serwerowa) służący do kolejkowania i wysyłania notyfikacji. Program powinien obsługiwać domyślne wyjątki oraz implementację przynajmniej jednego wyjątku związanego z walidacją treści wprowadzanych przez użytkownika w aplikacji klienckiej.

Funkcje aplikacji klienckiej:

połączenie z serwerem wraz z walidacją i obsługą wyjątków
pobranie od użytkownika treści notyfikacji (wiadomość tekstowa) oraz czasu odesłania notyfikacji do użytkownika
wyświetlenie otrzymanej przez serwer notyfikacji
Funkcje aplikacji serwerowej:

obsługa wielu klientów jednocześnie
przyjmowanie wysłanych z aplikacji klienckiej notyfikacji i kolejkowanie ich na serwerze
wysyłanie notyfikacji do klienta który ją zapisał o podanym przez niego czasie
