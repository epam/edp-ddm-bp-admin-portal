{{- define "camunda-cockpit.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{- define "edp.hostnameSuffix" -}}
{{- printf "%s-%s.%s" .Values.cdPipelineName .Values.cdPipelineStageName .Values.dnsWildcard }}
{{- end }}

{{- define "camunda-cockpit.hostname" -}}
{{- $hostname := default (include "camunda-cockpit.fullname" .) }}
{{- printf "%s-%s" $hostname (include "edp.hostnameSuffix" .) }}
{{- end }}

{{- define "keycloak.officerRealm" -}}
{{- printf "%s-%s" .Values.namespace .Values.keycloak.officerRealm.name }}
{{- end -}}

{{- define "keycloak.adminRealm" -}}
{{- printf "%s-%s" .Values.namespace .Values.keycloak.adminRealm.name }}
{{- end -}}

{{- define "camunda-cockpit.url" -}}
{{- printf "%s%s-%s-%s.%s" "https://" "business-proc-admin" .Values.cdPipelineName .Values.cdPipelineStageName .Values.dnsWildcard }}
{{- end }}

{{- define "keycloak.url" -}}
{{- printf "%s%s" "https://" .Values.keycloak.host }}
{{- end -}}

{{/*
Define Jenkins URL
*/}}
{{- define "jenkins-operator.jenkinsBaseUrl" -}}
{{- printf "%s-%s-%s" "jenkins" .Values.edpProject (include "edp.hostnameSuffix" .) }}
{{- end }}

{{/*
Define Jenkins URL
*/}}
{{- define "jenkins-operator.jenkinsUrl" -}}
{{- printf "%s%s" "https://" (include "jenkins-operator.jenkinsBaseUrl" .) }}
{{- end }}

{{- define "admin-routes.whitelist.cidr" -}}
{{- if .Values.global }}
{{- if .Values.global.whiteListIP }}
{{- .Values.global.whiteListIP.adminRoutes }}
{{- end }}
{{- end }}
{{- end -}}

{{- define "admin-routes.whitelist.annotation" -}}
haproxy.router.openshift.io/ip_whitelist: {{ (include "admin-routes.whitelist.cidr" . | default "0.0.0.0/0") | quote }}
{{- end -}}
