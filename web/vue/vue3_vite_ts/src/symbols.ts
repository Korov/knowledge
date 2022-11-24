import { InjectionKey, Ref } from 'vue'
import MenuData from '@/types'

const MenuDataKey: InjectionKey<Ref<MenuData[]>> = Symbol('MenuData')

export default MenuDataKey
