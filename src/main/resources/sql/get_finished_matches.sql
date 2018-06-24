select
  m.id match_id, m.title match_title,
  m.game_id, g.title game_title,
  m.status_id, s.title status_title, s.finalized status_finalized
from
  matches m
inner join
  game g on g.id = m.game_id
inner join
  status s on s.id = m.status_id
where
  m.status_id != 0
order by
  m.id desc