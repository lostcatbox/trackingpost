import { createRouter, createWebHistory } from 'vue-router'
import PostDetails from '@/views/posts/PostDetails.vue'
//BoardList.vue를 BoardList로 import
import Recodes from'@/views/posts/Recodes.vue';

const routes = [
    {
        path: '/:userId/:postNumber',
        name: 'PostDetails',
        component: PostDetails
    },
    {
        path: '/recodes',
        name: 'Recodes',
        component: Recodes
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
