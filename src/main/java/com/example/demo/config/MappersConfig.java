package com.example.demo.config;
import com.example.demo.Entities.*;
import com.example.demo.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappersConfig {
    @Bean
    public  ModelMapper modelMapper(){
        ModelMapper modelMapper=new ModelMapper();

        // Configuración para mapear BancoEntity a Banco utilizando el singleton
        modelMapper.addMappings(new PropertyMap<PartidaEntity, Partida>() {
            @Override
            protected void configure() {
                using(new AbstractConverter<BancoEntity, Banco>() {
                    @Override
                    protected Banco convert(BancoEntity bancoEntity) {
                        Banco banco = Banco.getBancoInstance();
                        banco.reiniciarBanco();
                        banco.setId(bancoEntity.getId());
                        banco.setCantidadChacras(bancoEntity.getCantidadChacras());
                        banco.setCantidadEstancias(bancoEntity.getCantidadEstancias());
                        banco.setCantidadPropiedades(bancoEntity.getCantidadPropiedades());
                        return banco;
                    }
                }).map(source.getBanco(), destination.getBanco());
            }
        });
        return modelMapper;
    }
    @Bean("mergerMapper")
    public  ModelMapper mergerMapper(){
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
