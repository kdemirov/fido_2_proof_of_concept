create view discussion_view as
    select
           d.id,
           d.name,
           d.description,
           du."name" as creator_name
    from discussion as d
        inner join discussion_users as du on d.creator_id=du.id;