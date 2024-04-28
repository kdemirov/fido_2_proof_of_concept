### Database Views

- Discussion View 

`create view discussion_view
as 
select 
d.id,d.name,d.description,du."name" as creator_name
from discussion as d 
inner join discussion_users as du on d.creator_id=du.id;`

- Comment View 

`create view comment_view 
as 
select
c.id,c.body,du."name" ,c.submission_date,d.id as discussion_id
from "comment" as c 
inner join discussion as d on c.discussion_id=d.id
inner join discussion_users as du on c.author_id=du.id ;`
