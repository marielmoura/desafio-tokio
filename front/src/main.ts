import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

createApp(App).use(router).mount('#app')

router.beforeEach((to, _from, next) => {
	const publicPages = ['/login']
	const authRequired = !publicPages.includes(to.path)
	const token = localStorage.getItem('token')

	if (authRequired && !token) {
		return next('/login')
	}

	next()
})