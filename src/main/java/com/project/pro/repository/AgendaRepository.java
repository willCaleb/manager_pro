package com.project.pro.repository;

import com.project.pro.model.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Integer>, JpaSpecificationExecutor<Agenda>{

    @Query(value = " " +
            "select ag.* from pro_agenda ag" +
            "   where (ag.data_inicio between :dataInicio and :dataFim " +
            "       or ag.data_fim between :dataInicio and :dataFim) " +
            "   or (:dataInicio, :dataFim) overlaps (ag.data_inicio, ag.data_fim)" +
            "   and ag.id_profissional = :profissional", nativeQuery = true)
    List<Agenda> buscarAgendaEntreHorarios(@Param("dataInicio")Date dataInicio, @Param("dataFim") Date dataFim, @Param("profissional") Integer profissional);

}
