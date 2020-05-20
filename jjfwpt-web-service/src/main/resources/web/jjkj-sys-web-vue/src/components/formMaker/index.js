import FormMaker from './FormMaker'

const install = function(Vue, opts = {}) {
    Vue.component('FormMaker', FormMaker)
}

const API = {
    install,
    version: '1.0.4',
}

if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue)
}

export default API
