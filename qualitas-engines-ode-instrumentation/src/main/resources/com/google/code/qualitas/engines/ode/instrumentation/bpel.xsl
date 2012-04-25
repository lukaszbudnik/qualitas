<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:bpws="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
	xmlns:xalan="http://xml.apache.org/xalan"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	exclude-result-prefixes="xalan">
	<xsl:output method="xml" indent="yes" xalan:indent-amount="2" />
	<xsl:strip-space elements="*" />

	<!-- 

	MAIN TEMPLATE
	
	 -->
	<xsl:template match="/">

		<xsl:apply-templates />

	</xsl:template>

	<!-- 

	RECIEVE - adds assign activity which initialises monitor specific variables

	-->
	<xsl:template
		match="*[local-name() = 'receive' and namespace-uri() = 'http://docs.oasis-open.org/wsbpel/2.0/process/executable']">

		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>

		<xsl:call-template name="set-monitor-variable" />

	</xsl:template>

	<!-- 
	
	VARIABLE - appends new variables at the end of current variables list

	-->
	<xsl:template
		match="*[local-name() = 'variable' and namespace-uri() = 'http://docs.oasis-open.org/wsbpel/2.0/process/executable' and position() = last()]">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
		<bpws:variable name="QualitasProcessInstanceId" type="xsd:long" />
	</xsl:template>

	<!-- 

	ALL OTHER - copies all other nodes 

	-->
	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>

	<xsl:template name="set-monitor-variable">
		<bpws:assign name="QualitasProcessInstanceIdAssign">
		  	<bpws:copy>
				<bpws:from header="qualitasProcessInstanceId">
					<xsl:attribute name="variable"><xsl:value-of select="//bpws:receive/@variable" /></xsl:attribute>
				</bpws:from>
				<bpws:to variable="QualitasProcessInstanceId" />
			</bpws:copy>
		</bpws:assign>
	</xsl:template>

</xsl:stylesheet>
