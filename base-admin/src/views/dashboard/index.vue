<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>用户总数</span>
            </div>
          </template>
          <div class="card-body">
            <div class="card-value">{{ userCount }}</div>
            <div class="card-icon">
              <el-icon><User /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>系统消息</span>
            </div>
          </template>
          <div class="card-body">
            <div class="card-value">{{ messageCount }}</div>
            <div class="card-icon">
              <el-icon><Message /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>待办事项</span>
            </div>
          </template>
          <div class="card-body">
            <div class="card-value">{{ todoCount }}</div>
            <div class="card-icon">
              <el-icon><List /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="dashboard-card">
          <template #header>
            <div class="card-header">
              <span>系统状态</span>
            </div>
          </template>
          <div class="card-body">
            <div class="card-value">正常</div>
            <div class="card-icon">
              <el-icon><Monitor /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mt-20">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>最近登录记录</span>
            </div>
          </template>
          <el-table :data="loginLogs" stripe style="width: 100%">
            <el-table-column prop="username" label="用户名" />
            <el-table-column prop="loginTime" label="登录时间" />
            <el-table-column prop="ip" label="IP地址" />
            <el-table-column prop="status" label="状态">
              <template #default="scope">
                <el-tag :type="scope.row.status === '成功' ? 'success' : 'danger'">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>系统信息</span>
            </div>
          </template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="系统版本">v1.0.0</el-descriptions-item>
            <el-descriptions-item label="服务器环境">Spring Boot 3.0</el-descriptions-item>
            <el-descriptions-item label="数据库版本">MySQL 8.0</el-descriptions-item>
            <el-descriptions-item label="操作系统">CentOS 7.9</el-descriptions-item>
            <el-descriptions-item label="Java版本">JDK 17</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { User, Message, List, Monitor } from '@element-plus/icons-vue'

// 模拟数据
const userCount = ref(128)
const messageCount = ref(25)
const todoCount = ref(12)

const loginLogs = ref([
  {
    username: 'admin',
    loginTime: '2024-03-20 14:30:00',
    ip: '192.168.1.100',
    status: '成功'
  },
  {
    username: 'test',
    loginTime: '2024-03-20 14:25:00',
    ip: '192.168.1.101',
    status: '失败'
  },
  {
    username: 'user1',
    loginTime: '2024-03-20 14:20:00',
    ip: '192.168.1.102',
    status: '成功'
  }
])
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 20px;
  
  .mt-20 {
    margin-top: 20px;
  }

  .dashboard-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .card-body {
      position: relative;
      height: 100px;
      display: flex;
      align-items: center;
      
      .card-value {
        font-size: 30px;
        font-weight: bold;
        color: #303133;
      }
      
      .card-icon {
        position: absolute;
        right: 20px;
        font-size: 50px;
        color: #409eff;
        opacity: 0.3;
      }
    }
  }

  :deep(.el-card__header) {
    padding: 15px 20px;
    border-bottom: 1px solid #ebeef5;
    box-sizing: border-box;
  }
}
</style> 