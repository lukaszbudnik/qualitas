<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xalan="http://xml.apache.org/xalan"
	exclude-result-prefixes="xalan">
	<xsl:output method="xml" indent="yes" xalan:indent-amount="2" />
	<xsl:strip-space elements="*" />
	<xsl:param name="param" select="'programatically overriden ;-)'" />
	<xsl:variable name="test_xxx" select="'xalan rules'" />
	<xsl:template match="/">
		<xsl:copy>
			<xsl:apply-templates select="node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="there">
		<xsl:variable name="dynamic_var">
			<xsl:value-of select="concat('$', 'test_', 'xxx')" />
		</xsl:variable>
		<there>
			<xsl:copy-of select="@*" />
			<xsl:attribute name="param"><xsl:value-of select="$param"/></xsl:attribute>
			<xsl:attribute name="variable"><xsl:value-of select="xalan:evaluate($dynamic_var)" /></xsl:attribute>
			<xsl:apply-templates select="node()" />
		</there>
		<out>
			<xsl:copy-of select="xalan:checkEnvironment()" />
		</out>
	</xsl:template>
	<!-- skip out elements -->
	<xsl:template match="out" />
	<xsl:template match="*">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>