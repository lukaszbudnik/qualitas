package com.google.code.qualitas.webapp;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.code.qualitas.engines.api.core.ProcessType;
import com.google.code.qualitas.integration.api.InstallationException;
import com.google.code.qualitas.integration.api.InstallationService;

/**
 * The Class HomeController.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    /** The installation service. */
    @Autowired
    private InstallationService installationService;

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLog(HomeController.class);

    /**
     * Index.
     * 
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", "Hello World!");
        return "home/index";
    }

    /**
     * Index upload.
     * 
     * @param model
     *            the model
     * @param name
     *            the name
     * @param file
     *            the file
     * @return the string
     * @throws IOException
     *             the IO exception
     * @throws InstallationException
     *             the installation exception
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String indexUpload(Model model, @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws IOException, InstallationException {

        LOG.debug("Uploaded file " + file.getOriginalFilename());

        byte[] bundle = IOUtils.toByteArray(file.getInputStream());

        installationService.install(bundle, file.getContentType(),
                ProcessType.WS_BPEL_2_0_APACHE_ODE);

        LOG.debug("File sent");

        model.addAttribute("message", file.getOriginalFilename());

        return "home/index";
    }

}
