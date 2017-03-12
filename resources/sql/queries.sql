-- :name create-todo! :! :n
-- :doc creates a todo
INSERT INTO todo
(id, state, description)
VALUES (:id, :state, :description)

-- :name update-user! :! :n
-- :doc update an existing todo as completed/not completed
UPDATE todo
SET state = :state
WHERE id = :id

-- :name get-todo :? :1
-- :doc retrieve a user given the id.
SELECT * FROM todo
WHERE id = :id

-- :name delete-todo! :! :n
-- :doc delete a todo given the id
DELETE FROM todo
WHERE id = :id
