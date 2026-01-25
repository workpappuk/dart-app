-- Communities table
CREATE TABLE communities (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    marked_for_deletion BOOLEAN NOT NULL DEFAULT FALSE,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id)
);

CREATE INDEX idx_communities_name ON communities(name);
CREATE INDEX idx_communities_created_by ON communities(created_by);

-- Posts table
CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    community_id BIGINT NOT NULL REFERENCES communities(id) ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES users(id),
    marked_for_deletion BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    updated_by BIGINT REFERENCES users(id)
);

CREATE INDEX idx_posts_community_id ON posts(community_id);
CREATE INDEX idx_posts_author_id ON posts(author_id);

-- Comments table
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    content VARCHAR(2048) NOT NULL,
    target_id BIGINT NOT NULL,
    target_type VARCHAR(32) NOT NULL,
    author_id BIGINT NOT NULL REFERENCES users(id),
    marked_for_deletion BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    updated_by BIGINT REFERENCES users(id)
);

CREATE INDEX idx_comments_target_id_type ON comments(target_id, target_type);
CREATE INDEX idx_comments_author_id ON comments(author_id);

-- Votes table (supports voting on posts and comments)
CREATE TABLE votes (
    id BIGSERIAL PRIMARY KEY,
    target_id BIGINT NOT NULL,
    target_type VARCHAR(32) NOT NULL, -- 'POST' or 'COMMENT'
    user_id BIGINT NOT NULL REFERENCES users(id),
    upvote BOOLEAN NOT NULL,
    UNIQUE (target_id, target_type, user_id)
);

CREATE INDEX idx_votes_target_id_type_user_id ON votes(target_id, target_type, user_id);
CREATE INDEX idx_votes_user_id ON votes(user_id);
