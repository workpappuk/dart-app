-- Migration: Add marked_for_deletion column to todos table
ALTER TABLE todos ADD COLUMN marked_for_deletion BOOLEAN DEFAULT FALSE;