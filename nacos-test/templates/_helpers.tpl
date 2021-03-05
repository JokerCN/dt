{{/*
Expand the name of the chart.
*/}}
{{- define "nacos-test.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "nacos-test.fullname" -}}
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

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "nacos-test.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "nacos-test.labels" -}}
helm.sh/chart: {{ include "nacos-test.chart" . }}
{{ include "nacos-test.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "nacos-test.selectorLabels" -}}
app: {{ include "nacos-test.fullname" . }}
groups: {{ default (include "nacos-test.fullname" .) .Values.groups }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "nacos-test.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "nacos-test.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{/*
如果charts.version等于0.1.0则使用values中定义的镜像tag
如果charts.version不等于0.1.0则使用指定版本号作为镜像tag
*/}}
{{- define "nacos-test.tag" -}}
{{- if eq .Chart.Version "0.1.0" }}
{{- .Values.image.tag -}}
{{- else -}}
{{- .Chart.Version -}}
{{- end }}
{{- end -}}
