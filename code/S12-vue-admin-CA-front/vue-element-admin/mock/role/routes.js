// Just a mock data

const constantRoutes = [
  {
    path: '/redirect',
    component: 'layout/Layout',
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: 'views/redirect/index'
      }
    ]
  },
  {
    path: '/login',
    component: 'views/login/index',
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: 'views/login/auth-redirect',
    hidden: true
  },
  {
    path: '/404',
    component: 'views/error-page/404',
    hidden: true
  },
  {
    path: '/401',
    component: 'views/error-page/401',
    hidden: true
  },
  {
    path: '',
    component: 'layout/Layout',
    redirect: 'home',
    children: [
      {
        path: 'home',
        component: 'views/home/index',
        name: 'home',
        meta: { title: 'home', icon: 'home', affix: true }
      }
    ]
  },

]

const asyncRoutes = [
  // {
  //   path: '/permission',
  //   component: 'layout/Layout',
  //   redirect: '/permission/index',
  //   alwaysShow: true,
  //   meta: {
  //     title: 'Permission',
  //     icon: 'lock',
  //     roles: ['jinwei', 'admin', 'editor']
  //   },
  //   children: [
  //     {
  //       path: 'page',
  //       component: 'views/permission/page',
  //       name: 'PagePermission',
  //       meta: {
  //         title: 'Page Permission',
  //         roles: ['jinwei', 'admin']
  //       }
  //     },
  //     {
  //       path: 'directive',
  //       component: 'views/permission/directive',
  //       name: 'DirectivePermission',
  //       meta: {
  //         title: 'Directive Permission'
  //       }
  //     },
  //     {
  //       path: 'role',
  //       component: 'views/permission/role',
  //       name: 'RolePermission',
  //       meta: {
  //         title: 'Role Permission',
  //         roles: ['jinwei', 'admin']
  //       }
  //     }
  //   ]
  // },

  // {
  //   path: '/nested',
  //   component: 'layout/Layout',
  //   redirect: '/nested/menu1/menu1-1',
  //   name: 'Nested',
  //   meta: {
  //     title: 'Nested',
  //     icon: 'nested'
  //   },
  //   children: [
  //     {
  //       path: 'menu1',
  //       component: 'views/nested/menu1/index',
  //       name: 'Menu1',
  //       meta: { title: 'Menu1' },
  //       redirect: '/nested/menu1/menu1-1',
  //       children: [
  //         {
  //           path: 'menu1-1',
  //           component: 'views/nested/menu1/menu1-1',
  //           name: 'Menu1-1',
  //           meta: { title: 'Menu1-1' }
  //         },
  //         {
  //           path: 'menu1-2',
  //           component: 'views/nested/menu1/menu1-2',
  //           name: 'Menu1-2',
  //           redirect: '/nested/menu1/menu1-2/menu1-2-1',
  //           meta: { title: 'Menu1-2' },
  //           children: [
  //             {
  //               path: 'menu1-2-1',
  //               component: 'views/nested/menu1/menu1-2/menu1-2-1',
  //               name: 'Menu1-2-1',
  //               meta: { title: 'Menu1-2-1' }
  //             },
  //             {
  //               path: 'menu1-2-2',
  //               component: 'views/nested/menu1/menu1-2/menu1-2-2',
  //               name: 'Menu1-2-2',
  //               meta: { title: 'Menu1-2-2' }
  //             }
  //           ]
  //         },
  //         {
  //           path: 'menu1-3',
  //           component: 'views/nested/menu1/menu1-3',
  //           name: 'Menu1-3',
  //           meta: { title: 'Menu1-3' }
  //         }
  //       ]
  //     },
  //     {
  //       path: 'menu2',
  //       name: 'Menu2',
  //       component: 'views/nested/menu2/index',
  //       meta: { title: 'Menu2' }
  //     }
  //   ]
  // },

  {
    path: '/example',
    component: 'layout/Layout',
    redirect: '/example/list',
    name: 'Example',
    meta: {
      title: 'Example',
      icon: 'example'
    },
    children: [
      {
        path: 'create',
        component: 'views/example/create',
        name: 'CreateArticle',
        meta: { title: 'Create Article', icon: 'edit' }
      },
      {
        path: 'edit/:id(\\d+)',
        component: 'views/example/edit',
        name: 'EditArticle',
        meta: { title: 'Edit Article', noCache: true },
        hidden: true
      },
      {
        path: 'list',
        component: 'views/example/list',
        name: 'ArticleList',
        meta: { title: 'Article List', icon: 'list' }
      }
    ]
  },

  {
    path: '/tab',
    component: 'layout/Layout',
    children: [
      {
        path: 'index',
        component: 'views/tab/index',
        name: 'Tab',
        meta: { title: 'Tab', icon: 'tab' }
      }
    ]
  },

  // {
  //   path: '/theme',
  //   component: 'layout/Layout',
  //   redirect: 'noRedirect',
  //   children: [
  //     {
  //       path: 'index',
  //       component: 'views/theme/index',
  //       name: 'Theme',
  //       meta: { title: 'Theme', icon: 'theme' }
  //     }
  //   ]
  // },

  {
    path: '/i18n',
    component: 'layout/Layout',
    children: [
      {
        path: 'index',
        component: 'views/i18n-demo/index',
        name: 'I18n',
        meta: { title: 'I18n', icon: 'international' }
      }
    ]
  },

  {
    path: 'external-link',
    component: 'layout/Layout',
    children: [
      {
        path: 'https://github.com/JYLinOK/Spring-Tutorial',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

module.exports = {
  constantRoutes,
  asyncRoutes
}
