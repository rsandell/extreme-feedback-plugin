package org.jenkinsci.plugins.extremefeedback.UserLampsProperty

import lib.JenkinsTagLib
import lib.LayoutTagLib

def l=namespace(LayoutTagLib)
def t=namespace(JenkinsTagLib)
def st=namespace("jelly:stapler")

l.layout() {
    l.header() {
        st.bind(var: "it", value: my)
        script(src: "/plugin/extreme-feedback/angular.min.js")
        script(src: "/plugin/extreme-feedback/findlamps.js")
        link(rel: "stylesheet", type: "text/css", href:"/plugin/extreme-feedback/style.css")
    }
    l.main_panel() {
        div(id:"xf-app-container", "ng-app": "xfApp") {
            div("ng-controller": "xfController") {

                h1 {
                    text(my.displayName)
                }

                div {
                    p {
                        text("Order your lamps at ")
                        a(href: "http://www.gitgear.com/xfd") {
                            text("gitgear.com/xfd")
                        }
                    }
                }

                table(class:"sg-choice") {
                    thead {
                        tr {
                            th {
                                text("Add a lamp by IP address")
                            }
                        }
                    }
                    tbody {
                        tr {
                            td {
                                div(id: "add-lamp", "ng-show":"ipToggle") {
                                    input(type: "text", id: "add-lamp-input", "ng-model": "ipAddress", "ng-click": "updateIpContent()")
                                    button("ng-click": "addLamp(ipAddress);", "Add Lamp")
                                }
                                div(id: "spinner2", style: "display: none", "ng-hide":"ipToggle") {
                                    text("Searching...")
                                    t.progressBar(pos:-1)
                                }
                            }
                        }
                    }
                }
                table(class:"sg-table", style: "text-align: left", "ng-show": "lamps.length") {
                    thead {
                        tr {
                            th {
                                text("Active")
                            }
                            th {
                                text("MAC Address")
                            }
                            th {
                                text("IP Address")
                            }
                            th {
                                text("Name")
                            }
                            th {
                                text("Jobs Assigned To")
                            }
                            th {
                                text("Alarm")
                            }
                            th {
                                text("SFX")
                            }
                            th {
                                text("Remove")
                            }
                        }
                    }
                    tbody {
                        tr("ng-repeat": "lamp in lamps | orderBy:['macAddress']") {
                            td {
                                input(type: "checkbox", "ng-model": "lamp.inactive", "ng-change": "changeLamp(lamp)", "inverted": "")
                            }
                            td {
                                text("{{lamp.macAddress}}")
                            }
                            td {
                                text("{{lamp.ipAddress}}")
                            }
                            td {
                                input(type: "text", "ng-model": "lamp.name", "ng-enter": "changeLamp(lamp)")
                                img(src: "/plugin/extreme-feedback/pencil.png", "ng-click": "changeLamp(lamp)", style: "cursor: pointer;")
                            }
                            td {
                                table(class: "jobs") {
                                    tr("ng-repeat": "job in lamp.jobs", "ng-mouseover": "showRemove = true", "ng-mouseleave": "showRemove = false") {
                                        td {
                                            text("{{job}}")
                                        }
                                        td(align: "right", class: "delete") {
                                            img(src: "/plugin/extreme-feedback/remove.png", "ng-click": "removeProjectFromLamp(job, lamp)", style: "display:none;", "ng-show": "showRemove")
                                        }
                                    }
                                    tr {
                                        td(colspan: "2") {
                                            typeahead("items": "projects", "btntxt": "Add", "context": "lamp", "action": "addProjectToLamp(arg1, arg2)")
                                        }
                                    }
                                }
                            }
                            td {
                                input(type: "checkbox", "ng-model": "lamp.noisy", "ng-change": "changeLamp(lamp)")
                            }
                            td {
                                input(type: "checkbox", "ng-model": "lamp.sfx", "ng-change": "changeLamp(lamp)")
                            }
                            td {
                                img(src: "/plugin/extreme-feedback/remove.png", "ng-click": "removeLamp(lamp)", style: "cursor: pointer;")
                            }
                        }
                    }
                }
                div("ng-show": "!lamps.length") {
                    text("No lamps registered yet.")
                }
            }
        }
    }
}
