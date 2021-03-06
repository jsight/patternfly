package uk.me.eastmans.patternfly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.me.eastmans.patternfly.domain.Project;
import uk.me.eastmans.patternfly.services.ProjectService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Collections;

/**
 * Created by meastman on 08/04/16.
 */
@Controller
public class ProjectsController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/projects")
    public String listProjects(Model model)
    {
        Iterable<Project> projects = projectService.listAllProjects();

        model.addAttribute("projects", projects);
        return "projects";
    }

    @RequestMapping("/project/{id}")
    public String viewProject(Model model, @PathVariable Long id)
    {
        // Try to get the project
        Project project = projectService.getProjectById(id);

        model.addAttribute("project", project);
        return "project";
    }

    @RequestMapping("/addProject")
    public String addProject(Model model)
    {
        model.addAttribute("project", new Project());
        return "editProject";
    }

    @RequestMapping("/editProject/{id}")
    public String editProject(Model model, @PathVariable Long id) {
        // Try to get the project
        Project project = projectService.getProjectById(id);

        model.addAttribute("project", project);
        return "editProject";
    }

    @RequestMapping(value="/project",method= RequestMethod.POST)
    public String createProject(@Valid @ModelAttribute Project p, BindingResult bindingResult, Model model)
    {
        // Validate the project the one from the view does not have all
        // the componenets loaded so get again and update the fields
        if (!bindingResult.hasErrors()) {
            Project realProject = projectService.getProjectById(p.getId());
            realProject.setName(p.getName());
            realProject.setLocalFileLocation(p.getLocalFileLocation());
            projectService.saveProject(realProject);
            return "redirect:/projects";
        }

        return "editProject";
    }

    @RequestMapping("/project/{id}/technologies")
    public String viewTechnologies(Model model, @PathVariable Long id)
    {
        // Try to get the project
        Project project = projectService.getProjectById(id);

        model.addAttribute("project", project);
        return "technologies";
    }

    @RequestMapping("/project/{id}/issues")
    public String viewIssues(Model model, @PathVariable Long id)
    {
        // Try to get the project
        Project project = projectService.getProjectById(id);

        model.addAttribute("project", project);
        return "issues";
    }

    @RequestMapping("/project/{id}/dependencies")
    public String viewDependencies(Model model, @PathVariable Long id)
    {
        // Try to get the project
        Project project = projectService.getProjectById(id);

        model.addAttribute("project", project);
        return "dependencies";
    }


}
