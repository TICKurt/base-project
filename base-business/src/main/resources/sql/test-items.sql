-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建测试表
CREATE TABLE IF NOT EXISTS test_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- 插入测试用户数据（如果表为空）
INSERT INTO users (username, password, role)
SELECT 'admin', 'admin123', 'admin' FROM dual
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, password, role)
SELECT 'user1', 'pass123', 'user' FROM dual
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user1');

INSERT INTO users (username, password, role)
SELECT 'user2', 'pass456', 'user' FROM dual
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user2');

-- 插入测试表数据（如果表为空）
INSERT INTO test_items (name, description, created_by)
SELECT '测试项目1', '这是管理员创建的测试项目', 1 FROM dual
WHERE NOT EXISTS (SELECT 1 FROM test_items WHERE name = '测试项目1');

INSERT INTO test_items (name, description, created_by)
SELECT '测试项目2', '这是用户1创建的测试项目', 2 FROM dual
WHERE NOT EXISTS (SELECT 1 FROM test_items WHERE name = '测试项目2');

INSERT INTO test_items (name, description, created_by)
SELECT '测试项目3', '这是用户2创建的测试项目', 3 FROM dual
WHERE NOT EXISTS (SELECT 1 FROM test_items WHERE name = '测试项目3'); 