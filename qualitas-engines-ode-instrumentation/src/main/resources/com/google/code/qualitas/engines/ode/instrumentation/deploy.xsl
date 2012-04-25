<xsl:stylesheet version="1.0"
	xmlns="http://www.apache.org/ode/schemas/dd/2007/03"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:monitor="http://qualitas.googlecode.com/internal/monitor/webservice"
	xmlns:xalan="http://xml.apache.org/xalan"
	exclude-result-prefixes="xalan">
	<xsl:output method="xml" indent="yes" xalan:indent-amount="2" />
	<xsl:strip-space elements="*" />

	<xsl:template match="/">

		<xsl:apply-templates />

	</xsl:template>

	<!-- override main deploy node, add new namespace (Logger namespace) -->
	<xsl:template
		match="*[local-name() = 'deploy' and namespace-uri() = 'http://www.apache.org/ode/schemas/dd/2007/03']">

		<deploy>
			<xsl:copy-of select="namespace::*" />
			<xsl:copy-of select="@*" />
			<xsl:apply-templates select="node()" />
		</deploy>

	</xsl:template>

	<!-- add invoke element just under the provide  -->
	<xsl:template
		match="*[local-name() = 'provide' and namespace-uri() = 'http://www.apache.org/ode/schemas/dd/2007/03'][position() = last()]">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
		<invoke partnerLink="QualitasMonitorService">
			<service name="monitor:QualitasMonitorService" port="QualitasMonitorPort"/>
		</invoke>
	</xsl:template>

	<!-- copy all other elements -->
	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>