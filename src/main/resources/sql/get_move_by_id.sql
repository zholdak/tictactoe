select
  o.id move_id, o.match_id, o.opponent move_opponent, o.position move_position, o.performed move_performed
from
  moves o
where
  o.id = :moveId
