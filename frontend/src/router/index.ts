import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Home from "../views/Home.vue";
import Profile from "../views/Profile.vue";
import Requisitions from "../views/Requisitions.vue";
import Logistics from "../views/Logistics.vue";
import HumanResources from "../views/HumanResources.vue";
import Sales from "../views/Sales.vue";
import Production from "../views/Production.vue";
import DepartmentOverview from "../views/DepartmentOverview.vue";
import HrManageUsers from "../components/HrManageUsers.vue";
import LogisticsManageOrders from "../components/LogisticsManageOrders.vue";
import LogisticsOrderMaterials from "../components/LogisticsOrderMaterials.vue";
import LogisticsOverview from "../components/LogisticsOverview.vue";
import RequisitionsInventory from "../components/RequisitionsInventory.vue";
import RequisitionsCreateOrder from "../components/RequisitionsCreateOrder.vue";
import RequisitionsOverview from "../components/RequisitionsOverview.vue";
import ProductionManagement from "../components/ProductionManagement.vue";
import ProductionOverview from "../components/ProductionOverview.vue";
import ProductionMaterials from "../components/ProductionMaterials.vue";
import ProductionStatistics from "../components/ProductionStatistics.vue";


import Main from "../views/Main.vue";
import { authGuard } from "@/auth/authGuard";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  },
  {
    path: "/profile",
    name: "profile",
    component: Profile,
    beforeEnter: authGuard
  },
  {
    path: "/DepartmentOverview",
    name: "departmentoverview",
    component: DepartmentOverview,
    beforeEnter: authGuard
  },
  {
    path: "/main",
    name: "Main",
    component: Main
  },
  {
    path: "/requisitions",
    name: "requisitions",
    component: Requisitions,
    beforeEnter: authGuard,
    children: [
      {
        path: "inventory",
        component: RequisitionsInventory,
        name: "requisitionsinventory",
        beforeEnter: authGuard
      },
      {
        path: "overview",
        component: RequisitionsOverview,
        name: "requisitionsoverview",
        beforeEnter: authGuard
      },
      {
        path: "createorder",
        component: RequisitionsCreateOrder,
        name: "requisitionscreateorder",
        beforeEnter: authGuard
      },
    ]
  },
  {
    path: "/logistics",
    name: "logistics",
    component: Logistics,
    beforeEnter: authGuard,
    children: [
      {
        path: "overview",
        component: LogisticsOverview,
        name: "logisticsoverview",
        beforeEnter: authGuard
      }, {
        path: "orders",
        component: LogisticsManageOrders,
        name: "logisticsmanageorders",
        beforeEnter: authGuard
      }, {
        path: "materials",
        component: LogisticsOrderMaterials,
        name: "logisticsordermaterials",
        beforeEnter: authGuard
      },
    ]
  },
  {
    path: "/hr",
    name: "hr",
    component: HumanResources,
    beforeEnter: authGuard,
    children: [
      {
        path: "users",
        component: HrManageUsers,
        name: "hrusers",
        beforeEnter: authGuard,
      }
    ]
  },
  {
    path: "/sales",
    name: "sales",
    component: Sales,
    beforeEnter: authGuard
  },
  {
    path: "/production",
    name: "production",
    component: Production,
    beforeEnter: authGuard,
    children: [
      {
        path: "management",
        component: ProductionManagement,
        name: "productionmanagement",
        props: true,
        beforeEnter: authGuard
      },
      {
        path: "overview",
        component: ProductionOverview,
        name: "productionoverview",
        beforeEnter: authGuard
      },
      {
        path: "materials",
        component: ProductionMaterials,
        name: "productionmaterials",
        beforeEnter: authGuard
      },
      {
        path: "statistics",
        component: ProductionStatistics,
        name: "productionstatistics",
        beforeEnter: authGuard
      },
    ]
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
