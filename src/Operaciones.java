import java.util.*;

/**
 * ArrayList ya funciona. Implementen las cinco operaciones para las otras tres
 * estructuras elegidas.
 */
public final class Operaciones {
    private Operaciones() {
    }

    private static UnsupportedOperationException pendiente(Estado e, String metodo) {
        return new UnsupportedOperationException("TODO: " + metodo + " para " + e.tipo);
    }

    public static Persona buscarPersona(Estado e, String id) {

        if (e.tipo == Estado.Tipo.ArrayList) {
            for (Persona p : e.personas.lista)
                if (p.id.equals(id))
                    return p;
            return null;
        }

        if (e.tipo == Estado.Tipo.TreeMap) {

            if (id == null)
                return null;

            return e.personas.mapa.get(id);
        }

        throw pendiente(e, "buscarPersona");
    }

    public static boolean corregirPais(
            Estado e,
            String idPersona,
            String codigoPais) {

        if (e.tipo == Estado.Tipo.ArrayList) {

            boolean existePais = false;

            for (Pais p : e.paises.lista)
                if (p.id.equals(codigoPais)) {
                    existePais = true;
                    break;
                }

            if (!existePais)
                return false;

            for (int i = 0; i < e.personas.lista.size(); i++) {

                Persona p = e.personas.lista.get(i);

                if (p.id.equals(idPersona)) {

                    if (p.codigoPais.equals(codigoPais))
                        return false;

                    e.personas.lista.set(
                            i,
                            new Persona(
                                    p.id,
                                    p.nombre,
                                    codigoPais));

                    return true;
                }
            }

            return false;
        }

        if (e.tipo == Estado.Tipo.TreeMap) {

            if (!e.paises.mapa.containsKey(codigoPais))
                return false;

            Persona persona = e.personas.mapa.get(idPersona);

            if (persona == null)
                return false;

            if (persona.codigoPais.equals(codigoPais))
                return false;

            Persona actualizada = new Persona(
                    persona.id,
                    persona.nombre,
                    codigoPais);

            e.personas.mapa.put(
                    persona.id,
                    actualizada);

            return true;
        }

        throw pendiente(e, "corregirPais");
    }

    public static boolean registrarInscripcion(
            Estado e,
            String codigo,
            String idPersona,
            String actividad) {

        if (e.tipo == Estado.Tipo.ArrayList) {

            if (codigo == null ||
                    codigo.trim().isEmpty() ||
                    !Datos.ACTIVIDADES.contains(actividad))
                return false;

            if (buscarPersona(e, idPersona) == null)
                return false;

            for (Inscripcion i : e.inscripciones.lista)

                if (i.id.equals(codigo) ||
                        (i.idPersona.equals(idPersona)
                                && i.actividad.equals(actividad)))
                    return false;

            e.inscripciones.lista.add(
                    new Inscripcion(
                            codigo,
                            idPersona,
                            actividad));

            return true;
        }

        if (e.tipo == Estado.Tipo.TreeMap) {

            if (codigo == null ||
                    codigo.trim().isEmpty())
                return false;

            if (!Datos.ACTIVIDADES.contains(actividad))
                return false;

            if (!e.personas.mapa.containsKey(idPersona))
                return false;

            if (e.inscripciones.mapa.containsKey(codigo))
                return false;

            for (Inscripcion i : e.inscripciones.mapa.values()) {

                if (i.idPersona.equals(idPersona)
                        && i.actividad.equals(actividad))
                    return false;
            }

            e.inscripciones.mapa.put(
                    codigo,
                    new Inscripcion(
                            codigo,
                            idPersona,
                            actividad));

            return true;
        }

        throw pendiente(e, "registrarInscripcion");
    }

    public static boolean cancelarInscripcion(
            Estado e,
            String codigo) {

        if (e.tipo == Estado.Tipo.ArrayList) {

            Iterator<Inscripcion> it = e.inscripciones.lista.iterator();

            while (it.hasNext())

                if (it.next().id.equals(codigo)) {
                    it.remove();
                    return true;
                }

            return false;
        }

        if (e.tipo == Estado.Tipo.TreeMap) {

            if (e.inscripciones.mapa.containsKey(codigo)) {

                e.inscripciones.mapa.remove(codigo);

                return true;
            }

            return false;
        }

        throw pendiente(e, "cancelarInscripcion");
    }

    public static Map<String, Integer> contarPersonasPorPais(
            Estado e) {

        if (e.tipo == Estado.Tipo.ArrayList) {

            Map<String, Integer> resultado = new TreeMap<>();

            for (Pais pais : e.paises.lista)
                resultado.put(pais.id, 0);

            for (Persona persona : e.personas.lista)

                resultado.put(
                        persona.codigoPais,
                        resultado.get(
                                persona.codigoPais) + 1);

            return resultado;
        }

        if (e.tipo == Estado.Tipo.TreeMap) {

            Map<String, Integer> resultado = new TreeMap<>();

            for (Pais pais : e.paises.mapa.values())
                resultado.put(pais.id, 0);

            for (Persona persona : e.personas.mapa.values()) {

                resultado.put(
                        persona.codigoPais,
                        resultado.get(
                                persona.codigoPais) + 1);
            }

            return resultado;
        }

        throw pendiente(e, "contarPersonasPorPais");
    }
}