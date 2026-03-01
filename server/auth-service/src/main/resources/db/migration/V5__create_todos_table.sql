CREATE TABLE todos (
    description VARCHAR(255) NOT NULL,
    completed BOOLEAN NOT NULL,
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    marked_for_deletion BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(36),
        updated_by varchar(36)
);
