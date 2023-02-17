package es.hito.control;

import es.hito.jpa.Role;
import es.hito.jpa.Tarea;
import es.hito.jpa.Usuario;
import es.hito.servicios.RoleService;
import es.hito.servicios.TareaRepository;
import es.hito.servicios.TareaService;
import es.hito.servicios.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class Controlador {
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UsuarioService usuarios;

    @Autowired
    RoleService roles;

    @Autowired
    TareaService tareas;
    @Autowired
    private TareaRepository tareaRepository;


    // Atiende petición localhost:8083/
    @RequestMapping("/")
    public ModelAndView peticionRaiz(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        mv.setViewName("index");

        return mv;
    }

    @RequestMapping("/denegado")
    public ModelAndView peticionDenegado(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        mv.setViewName("denegado");
        return mv;
    }

    @RequestMapping("login")
    public ModelAndView peticionSesion(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/user")
    public ModelAndView peticionUsuario(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        Usuario user=null;

        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else {
            mv.addObject("user", aut.getName());

            Optional<Usuario> userOptional = usuarios.buscarUsuario(aut.getName());

            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
        }

        mv.addObject("usuario", user);

        mv.setViewName("usuario");
        return mv;
    }

    @RequestMapping("/user/perfil")
    public ModelAndView peticionPerfil(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("perfil");
        return mv;
    }

    @RequestMapping("/user/tareas/nueva")
    public ModelAndView peticioNuevaTarea(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("nuevatarea");
        Tarea tarea = new Tarea();
        mv.addObject("tarea", tarea);
        return mv;
    }

    @RequestMapping("/admin/tareas/nueva")
    public ModelAndView peticioNuevaTareaAdmin(Authentication aut, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("nuevatareaAdmin");
        Tarea tarea = new Tarea();
        String nif = request.getParameter("nif");
        mv.addObject("tarea", tarea);
        mv.addObject("nif", nif);
        return mv;
    }

    @RequestMapping("/user/tareas/nueva/crear")
    public ModelAndView peticioCrearTarea(Authentication aut, Tarea tarea, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("nuevatarea");
        Optional<Usuario> userOptional = usuarios.buscarUsuario(aut.getName());

        Usuario user=null;
        if (userOptional.isPresent()) {
            System.out.println("Usuario encontrado");
            user = userOptional.get();
        }
        tarea.setUsuario(user);
        tareas.guardarTarea(tarea);
        mv.setViewName("redirect:/user/tareas/listado");
        return mv;
    }

    @RequestMapping("/admin/tareas/nueva/crear")
    public ModelAndView peticioCrearTareaAdmin(Authentication aut, Tarea tarea, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("nuevatarea");
        String nif = request.getParameter("nif");
        Optional<Usuario> userOptional = usuarios.buscarUsuario(request.getParameter("nif"));
        Usuario user=null;
        if (userOptional.isPresent()) {
            System.out.println("Usuario encontrado");
            user = userOptional.get();
        }
        tarea.setUsuario(user);
        tareas.guardarTarea(tarea);
        mv.setViewName("redirect:/admin");
        return mv;
    }

    @RequestMapping("/user/tareas/listado")
    public ModelAndView peticioListdoTareas(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        Optional<Usuario> userOptional = usuarios.buscarUsuario(aut.getName());
        Usuario user=null;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        List<Tarea> listaTareas = user.getTareas();
        mv.addObject("listaTareas", listaTareas);
        mv.setViewName("listadotareas");
        return mv;
    }

    @RequestMapping("/admin/tareas/eliminar")
    public ModelAndView peticioEliminarTareaAdmin(Authentication aut, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        String id = request.getParameter("id");
        tareas.borrarTarea(id);
        mv.setViewName("redirect:/admin");
        return mv;
    }

    @RequestMapping("/admin/tareas/editar")
    public ModelAndView peticioEditarTareaAdmin(Authentication aut, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        String id = request.getParameter("id");
        Tarea tarea = tareas.obtenerTareaPorId(id);
        mv.addObject("tarea", tarea);
        mv.setViewName("editartareaAdmin");
        return mv;
    }

    @RequestMapping("/admin/tareas/editar/actualizar")
    public ModelAndView peticioActualizarTareaAdmin(Authentication aut, Tarea tarea, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        String id = request.getParameter("id");
        System.out.println(tarea.getId());
        System.out.println(tarea.getDescripcion());
        System.out.println(tarea.getEstado());
        tareas.actualizarTarea(tarea);
        mv.setViewName("redirect:/admin");
        return mv;
    }

    @RequestMapping("/admin")
    public ModelAndView peticionAdmin(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        List<Usuario> listaUsuarios = usuarios.listaUsuarios();
        mv.addObject("listaUsuarios", listaUsuarios);
        mv.setViewName("administrador");
        return mv;
    }

    @RequestMapping("/admin/usuario/nuevo")
    public ModelAndView registro() {
        ModelAndView mv = new ModelAndView();
        Usuario c = new Usuario();
        mv.addObject("usuario", c);
        mv.setViewName("nuevousuario");
        return mv;
    }
    @RequestMapping("/guardar")
    public ModelAndView peticionGuardar(Usuario u, Authentication aut) {
        ModelAndView mv = new ModelAndView();
        System.out.println(u);

        String sincifrar = u.getPw();
        String cifrado = encoder.encode(sincifrar);
        u.setPw(cifrado);

        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        Optional<Usuario> usuarioBuscado = usuarios.buscarUsuario(u.getNif());

        if (usuarioBuscado.isPresent()) {
            mv.addObject("sms", "El nif " + u.getNif() + " ya está registrado");
        } else {
            usuarios.guardarUsuario(u);
            Role rol = new Role();
            rol.setUsuario(u);
            rol.setRol("USUARIO");
            roles.guardarRol(rol);

            mv.addObject("sms", "Usuario " + u.getNombre() + " registrado con éxito");
        }

        mv.setViewName("informa");
        return mv;
    }

    @RequestMapping("/admin/usuario/nuevaTarea")
    public ModelAndView registroTarea() {
        ModelAndView mv = new ModelAndView();
        Tarea t = new Tarea();
        mv.addObject("tarea", t);
        mv.setViewName("nuevatarea");
        return mv;
    }





    @RequestMapping("/addtarea")
    public ModelAndView peticionGuardarTarea(Tarea t, Authentication aut) {
        ModelAndView mv = new ModelAndView();
        System.out.println(t);

        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        Optional<Usuario> usuarioBuscado = usuarios.buscarUsuario(aut.getName());

        if (usuarioBuscado.isPresent()) {
            Usuario u = usuarioBuscado.get();
            t.setUsuario(u);
            tareas.guardarTarea(t);
            mv.addObject("sms", "Tarea " + t.getNombre() + " registrada con éxito");
        } else {
            mv.addObject("sms", "El usuario no existe");
        }

        mv.setViewName("informa");
        return mv;
    }



    @RequestMapping("/admin/dashboard")
    public ModelAndView peticioDashboard(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("dashboard");
        return mv;
    }

    @RequestMapping("/admin/usuario/mostrar")
    public ModelAndView peticioUsuariosMostrar(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("mostrarusuarios");
        return mv;
    }

    @RequestMapping("/admin/usuario/editar")
    public ModelAndView peticioUsuariosEditar(Authentication aut, HttpServletRequest request) {

        String nif = request.getParameter("nif");
        Optional<Usuario> usuarioOpt = usuarios.buscarUsuario(nif);
        Usuario user = usuarioOpt.get();

        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        mv.addObject("usuario", user);

        mv.setViewName("editarusuario");
        return mv;
    }

    @RequestMapping("/actualizar")
    public String peticionActualizar(Usuario u, Authentication aut) {

        usuarios.guardarUsuario(u);

        return "redirect:/admin";
    }

    @RequestMapping("/user/tareas/editar")
    public ModelAndView peticioTareasEditar(Authentication aut, HttpServletRequest request) {

        String id = request.getParameter("id");
        Tarea tarea = tareas.obtenerTareaPorId(id);

        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        mv.addObject("tarea", tarea);

        mv.setViewName("editartareaAdmin");
        return mv;
    }

    @RequestMapping("/user/tareas/eliminar")
    public String peticionEliminarTarea(Authentication aut, HttpServletRequest request) {

        String id = request.getParameter("id");
        tareas.borrarTarea(id);

        return "redirect:/user/tareas";
    }
}