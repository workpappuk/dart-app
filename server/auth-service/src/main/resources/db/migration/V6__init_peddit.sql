
-- Communities table
CREATE TABLE communities (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    marked_for_deletion BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(36),
    updated_by varchar(36)
);

CREATE INDEX idx_communities_name ON communities(name);
CREATE INDEX idx_communities_created_by ON communities(created_by);

-- Posts table
CREATE TABLE posts (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    community_id UUID NOT NULL REFERENCES communities(id) ON DELETE CASCADE,
    marked_for_deletion BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(36),
    updated_by varchar(36)
);

CREATE INDEX idx_posts_community_id ON posts(community_id);
CREATE INDEX idx_posts_created_by ON posts(created_by);

-- Comments table
CREATE TABLE comments (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    content VARCHAR(2048) NOT NULL,
    target_id UUID NOT NULL,
    target_type VARCHAR(32) NOT NULL,
    parent_comment UUID REFERENCES comments(id),
    marked_for_deletion BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(36),
    updated_by varchar(36)
);

CREATE INDEX idx_comments_target_id_type ON comments(target_id, target_type);
CREATE INDEX idx_comments_created_by ON comments(created_by);

-- Votes table (supports voting on posts and comments)
CREATE TABLE votes (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    target_id UUID NOT NULL,
    target_type VARCHAR(32) NOT NULL,
    upvote BOOLEAN NOT NULL,
    marked_for_deletion BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(36),
    updated_by varchar(36) ,
    UNIQUE (target_id, target_type, created_by)
);

CREATE INDEX idx_votes_target_id_type_user_id ON votes(target_id, target_type, created_by);
CREATE INDEX idx_votes_user_id ON votes(created_by);
