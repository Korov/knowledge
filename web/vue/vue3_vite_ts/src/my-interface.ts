/// <reference types="vite/client" />

interface SubNav {
  id: string
  title: string
  path: string
}
interface MenuData {
  id: string
  title: string
  subNav: SubNav[]
}
export default MenuData
