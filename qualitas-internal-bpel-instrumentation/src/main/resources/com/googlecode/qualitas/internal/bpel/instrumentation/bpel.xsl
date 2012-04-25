<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:bpws="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
	xmlns:monitor="http://qualitas.googlecode.com/internal/monitor/webservice"
	xmlns:monitorArtifacts="http://qualitas.googlecode.com/internal/monitor/webservice/artifacts"
	xmlns:xalan="http://xml.apache.org/xalan"
	xmlns:configuration="http://qualitas.googlecode.com/engines/api/configuration"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	exclude-result-prefixes="xalan">
	<xsl:output method="xml" indent="yes" xalan:indent-amount="2" />
	<xsl:strip-space elements="*" />
	<xsl:param name="realPathToNuntiusXML" select="'programatically overriden ;-)'" />
	<!-- 

	MAIN TEMPLATE
	
	 -->
	<xsl:template match="/">

		<xsl:apply-templates />

	</xsl:template>

	<!-- 
	
	PROCESS - overrides main process node, copies existing and adds automatically new namespaces

	-->
	<xsl:template
		match="*[local-name() = 'process' and namespace-uri() = 'http://docs.oasis-open.org/wsbpel/2.0/process/executable']">

		<bpws:process>
			<xsl:copy-of select="namespace::*" />
			<xsl:copy-of select="@*" />
			<xsl:attribute name="queryLanguage"><xsl:value-of select="'urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0'" /></xsl:attribute>
			<xsl:attribute name="expressionLanguage"><xsl:value-of select="'urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0'" /></xsl:attribute>

			<xsl:apply-templates select="node()" />

		</bpws:process>
	</xsl:template>

	<!-- 
	
	IMPORT - appends new imports at the end of current import list 
	
	-->
	<xsl:template
		match="*[local-name() = 'import' and namespace-uri() = 'http://docs.oasis-open.org/wsbpel/2.0/process/executable'][position() = last()]">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
		<bpws:import importType="http://schemas.xmlsoap.org/wsdl/" location="QualitasMonitorService.wsdl" namespace="http://qualitas.googlecode.com/internal/monitor/webservice"/>
		<bpws:import importType="http://schemas.xmlsoap.org/wsdl/" location="QualitasMonitorServiceArtifacts.wsdl" namespace="http://qualitas.googlecode.com/internal/monitor/webservice/artifacts"/>
	</xsl:template>

	<!-- 
	
	PARTNER LINK - appends new partnerLink at the end of current partnerLink list

	-->
	<xsl:template
		match="*[local-name() = 'partnerLink' and namespace-uri() = 'http://docs.oasis-open.org/wsbpel/2.0/process/executable' and position() = last()]">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	    <bpws:partnerLink name="QualitasMonitorService" partnerLinkType="monitorArtifacts:QualitasMonitorServicePartnerLinkType" partnerRole="QualitasMonitorServiceProvider"/>
	</xsl:template>

	<!-- 
	
	VARIABLE - appends new variables at the end of current variables list

	-->
	<xsl:template
		match="*[local-name() = 'variable' and namespace-uri() = 'http://docs.oasis-open.org/wsbpel/2.0/process/executable' and position() = last()]">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
		<bpws:variable name="QualitasMonitorServiceRequest" messageType="monitor:log" />
		<bpws:variable name="QualitasMonitorSequenceNumber" type="xsd:int" />
	</xsl:template>

	<!-- 

	ASSIGN - add assign activity just after QualitasProcessInstanceIdAssign assign element

	-->
	<xsl:template
		match="*[local-name() = 'assign' and namespace-uri() = 'http://docs.oasis-open.org/wsbpel/2.0/process/executable' and @name='QualitasProcessInstanceIdAssign']">

		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>

		<xsl:call-template name="set-monitor-variable" />

		<bpws:assign>
			<bpws:copy>
                <bpws:from>0</bpws:from>
                <bpws:to><![CDATA[$QualitasMonitorSequenceNumber]]></bpws:to>
            </bpws:copy>
		</bpws:assign>
		
	</xsl:template>

	<!-- 
	
	INVOKE - wrap invoke elements with proper assigns and invoke the monitor web service

	-->
	<xsl:template
		match="*[local-name() = 'invoke' and namespace-uri() = 'http://docs.oasis-open.org/wsbpel/2.0/process/executable']">

		<xsl:variable name="partner" select="@partnerLink" />
		<xsl:variable name="service" select="@operation" />
		<xsl:variable name="globalAnalysisEnabled" select="/bpws:process/qualitasConfiguration/analystConfiguration/@analysisEnabled"  />
		<xsl:variable name="partnerAnalysisEnabled" select="/bpws:process/qualitasConfiguration/analystConfiguration/partners/partner[@name = $partner]/@anlysisEnabled"  />
		<xsl:variable name="serviceAnalysisEnabled" select="/bpws:process/qualitasConfiguration/analystConfiguration/partners/partner[@name = $partner]/services/service[@name = $service]/@anlysisEnabled"  />
			
		<!-- if all *AnalysisEnabled are not set to false proceed -->
		<xsl:if test="not($globalAnalysisEnabled = 'false') and not($partnerAnalysisEnabled = 'false') and not($serviceAnalysisEnabled = 'false')">

			<xsl:variable name="tmpMep" select="/bpws:process/qualitasConfiguration/monitorConfiguration/partners/partner[@name = $partner]/services/service[@name = $service]/@mep" />
		
			<!-- <debug>
			<xsl:value-of select="'global logging = '" />
			<xsl:value-of select="$globalAnalysisEnabled" />
			<xsl:value-of select="', tmp Mep='" />
			<xsl:value-of select="$tmpMep"/>
			<xsl:value-of select="', PL='" />
			<xsl:value-of select="$partnerLink"/>
			<xsl:value-of select="', op='" />
			<xsl:value-of select="$operation"/>
			</debug> -->
		
			<xsl:variable name="mep">
				<xsl:choose>
					<xsl:when test="string-length($tmpMep) != 0">
						<xsl:value-of select="$tmpMep" />
					</xsl:when>
					<!-- default value -->
					<xsl:otherwise><xsl:value-of select="'in-out'" /></xsl:otherwise>
				</xsl:choose>
			</xsl:variable>


			<!-- create empty logger variable -->
			<xsl:call-template name="set-monitor-variable" />

			<!-- prepare and set partnerLink and operation specific values -->
			<xsl:call-template name="prepare-and-set-operation-variables">
				<xsl:with-param name="activity" select="." />
				<xsl:with-param name="mep" select="$mep" />
			</xsl:call-template>

			<!-- set the input timestamp -->
			<xsl:call-template name="set-input-timestamp" />

			<!-- if mep contains in --> 
			<xsl:if test="contains($mep, 'in')">
				<xsl:call-template name="set-input-variable">
					<xsl:with-param name="activity" select="." />
				</xsl:call-template>
			</xsl:if>
			
			<!-- copy original invocation -->
			<xsl:copy>
				<xsl:apply-templates select="node()|@*" />
			</xsl:copy>
			
			<!-- set the output timestamp -->
			<xsl:call-template name="set-output-timestamp" />
			
			<!-- if mep contains out -->
			<xsl:if test="contains($mep, 'out')">
				<xsl:call-template name="set-output-variable">
					<xsl:with-param name="activity" select="." />
				</xsl:call-template>
			</xsl:if>
			
			<!--  finally call the monitor! -->
			<xsl:call-template name="invoke-monitor" />
		</xsl:if>
		
	</xsl:template>

	<!-- 
	
	Skip qualitasConfiguration element
	
	 -->
	<xsl:template
		match="*[local-name() = 'qualitasConfiguration']">
	</xsl:template>

	<!-- 

	ALL OTHER - copy 

	-->
	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>

	<xsl:template name="set-monitor-variable">
		<bpws:assign>
			<bpws:copy>
				<bpws:from>
					<bpws:literal>
			            <log
			            	xmlns="http://qualitas.googlecode.com/internal/monitor/webservice">
			            	<qualitasProcessInstanceId />
			            	<EPR />
			            	<partner />
			            	<service />
			            	<mep />
			            	<invocationStep />
			            	<sequenceNumber />
		            		<inTimestamp />
		            		<outTimestamp />
			            	<input />
			            	<output />
			            </log>
					</bpws:literal>
				</bpws:from>
				<bpws:to><![CDATA[$QualitasMonitorServiceRequest.parameters]]></bpws:to>
			</bpws:copy>
		  	<bpws:copy>
                <bpws:from><![CDATA[$QualitasProcessInstanceId]]></bpws:from>
                <bpws:to part="parameters"
					variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:qualitasProcessInstanceId]]></bpws:query>
				</bpws:to>
            </bpws:copy>
		</bpws:assign>
	</xsl:template>

	<xsl:template name="prepare-and-set-operation-variables">
		<xsl:param name="activity" />
		<xsl:param name="mep" />
		<bpws:assign>
            <bpws:copy>
				<bpws:from><![CDATA[$QualitasMonitorSequenceNumber]]></bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:sequenceNumber]]></bpws:query>
				</bpws:to>
			</bpws:copy>
            <bpws:copy>
            	<bpws:from><![CDATA[$QualitasMonitorSequenceNumber + 1]]></bpws:from>
            	<bpws:to><![CDATA[$QualitasMonitorSequenceNumber]]></bpws:to>
            </bpws:copy>
            <bpws:copy>
				<bpws:from><bpws:literal><xsl:value-of select="concat('invocation-',  generate-id())"></xsl:value-of></bpws:literal></bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:invocationStep]]></bpws:query>
				</bpws:to>
			</bpws:copy>
			<bpws:copy>
				<bpws:from endpointReference="partnerRole">
					<xsl:attribute name="partnerLink">
						<xsl:value-of select="$activity/@partnerLink"></xsl:value-of>
					</xsl:attribute>
				</bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:EPR]]></bpws:query>
				</bpws:to>
			</bpws:copy>
			<bpws:copy>
				<bpws:from>
					<bpws:literal>
						<xsl:value-of select="$activity/@partnerLink" />
					</bpws:literal>
				</bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:partner]]></bpws:query>
				</bpws:to>
			</bpws:copy>
			<bpws:copy>
				<bpws:from>
					<bpws:literal>
						<xsl:value-of select="$activity/@operation" />
					</bpws:literal>
				</bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:service]]></bpws:query>
				</bpws:to>
			</bpws:copy>
			<bpws:copy>
				<bpws:from>
					<bpws:literal>
						<xsl:value-of select="$mep" />
					</bpws:literal>
				</bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:mep]]></bpws:query>
				</bpws:to>
			</bpws:copy>
		</bpws:assign>
	</xsl:template>

	<xsl:template name="set-input-timestamp">
		<bpws:assign>
			<bpws:copy>
				<bpws:from><![CDATA[current-dateTime()]]></bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:inTimestamp]]></bpws:query>
				</bpws:to>
			</bpws:copy>
		</bpws:assign>
	</xsl:template>

	<xsl:template name="set-input-variable">
		<xsl:param name="activity" />
		<bpws:assign>
			<bpws:copy>
				<bpws:from>$<xsl:value-of select="$activity/@inputVariable" /></bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:input]]></bpws:query>
				</bpws:to>
			</bpws:copy>
		</bpws:assign>
	</xsl:template>

	<xsl:template name="set-output-timestamp">
		<bpws:assign>
			<bpws:copy>
				<bpws:from><![CDATA[current-dateTime()]]></bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:outTimestamp]]></bpws:query>
				</bpws:to>
			</bpws:copy>
		</bpws:assign>
	</xsl:template>

	<xsl:template name="set-output-variable">
		<xsl:param name="activity" />
		<bpws:assign>
			<bpws:copy>
				<bpws:from>$<xsl:value-of select="$activity/@outputVariable" /></bpws:from>
				<bpws:to part="parameters" variable="QualitasMonitorServiceRequest">
					<bpws:query><![CDATA[monitor:output]]></bpws:query>
				</bpws:to>
			</bpws:copy>
		</bpws:assign>
	</xsl:template>

	<!-- invoke monitor -->
	<xsl:template name="invoke-monitor">
		<bpws:invoke inputVariable="QualitasMonitorServiceRequest"  
			operation="log" partnerLink="QualitasMonitorService"
			portType="monitor:QualitasMonitorServicePortType"/>
	</xsl:template>

</xsl:stylesheet>
