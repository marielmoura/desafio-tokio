import { createRouter, createWebHistory, type RouteLocationNormalized } from 'vue-router'
import LoginPage from '../components/LoginPage.vue'
import TransferManager from '../components/TransferManager.vue'

import type { NavigationGuardNext, RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: LoginPage },
  { path: '/transfers', component: TransferManager }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to: RouteLocationNormalized, _from: RouteLocationNormalized, next: NavigationGuardNext) => {
	const publicPages = ['/login']
	const authRequired = !publicPages.includes(to.path)
	const token = localStorage.getItem('token')

	if (authRequired && !token) {
		return next('/login')
	}

	next()
})

export default router
