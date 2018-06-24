insert into game (nickname,title,active) values ('tictactoe','TicTacToe',true);
insert into game (nickname,title) values ('gomoku','Gomoku');
insert into game (nickname,title) values ('go','Go');

insert into status (id,title,finalized) values (0,'In progress', false);
insert into status (id,title) values (-1,'Draw');
insert into status (id,title) values (1,'Won');

insert into matches (id, game_id, title) values (1, 42, 'X should wins');
insert into moves (match_id,opponent,position) values (1,'X',4);
insert into moves (match_id,opponent,position) values (1,'O',3);
insert into moves (match_id,opponent,position) values (1,'X',8);
insert into moves (match_id,opponent,position) values (1,'O',5);
insert into moves (match_id,opponent,position) values (1,'X',0);
update matches set status_id=1 where id=1;

insert into matches (id, game_id, title) values (2, 42, 'X should wins');
insert into moves (match_id,opponent,position) values (2,'X',4);
insert into moves (match_id,opponent,position) values (2,'O',0);
insert into moves (match_id,opponent,position) values (2,'X',6);
insert into moves (match_id,opponent,position) values (2,'O',5);
insert into moves (match_id,opponent,position) values (2,'X',8);
insert into moves (match_id,opponent,position) values (2,'O',1);

insert into matches (id, game_id, title) values (3, 42, 'Draw will be here');
insert into moves (match_id,opponent,position) values (3,'X',5);
insert into moves (match_id,opponent,position) values (3,'O',3);
insert into moves (match_id,opponent,position) values (3,'X',7);
insert into moves (match_id,opponent,position) values (3,'O',2);
insert into moves (match_id,opponent,position) values (3,'X',4);
insert into moves (match_id,opponent,position) values (3,'O',1);
insert into moves (match_id,opponent,position) values (3,'X',0);
insert into moves (match_id,opponent,position) values (3,'O',8);
insert into moves (match_id,opponent,position) values (3,'X',6);
update matches set status_id=-1 where id=3;

insert into matches (id, game_id, title) values (4, 42, 'Try it out!');
insert into moves (match_id,opponent,position) values (4,'X',4);

insert into matches (id, game_id, title) values (5, 42, 'Just do it');
insert into moves (match_id,opponent,position) values (5,'X',6);

insert into matches (id, game_id, title) values (6, 42, 'X will win again');
insert into moves (match_id,opponent,position) values (6,'X',0);
insert into moves (match_id,opponent,position) values (6,'O',4);
insert into moves (match_id,opponent,position) values (6,'X',3);
insert into moves (match_id,opponent,position) values (6,'O',1);
insert into moves (match_id,opponent,position) values (6,'X',7);
insert into moves (match_id,opponent,position) values (6,'O',5);
insert into moves (match_id,opponent,position) values (6,'X',6);
update matches set status_id=1 where id=6;

insert into matches (id, game_id, title) values (7, 42, 'O trying to win');
insert into moves (match_id,opponent,position) values (7,'X',4);
insert into moves (match_id,opponent,position) values (7,'O',0);
insert into moves (match_id,opponent,position) values (7,'X',5);
insert into moves (match_id,opponent,position) values (7,'O',3);
insert into moves (match_id,opponent,position) values (7,'X',7);
insert into moves (match_id,opponent,position) values (7,'O',6);
update matches set status_id=1 where id=7;
