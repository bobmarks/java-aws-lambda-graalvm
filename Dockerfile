FROM public.ecr.aws/amazonlinux/amazonlinux:2023

RUN yum -y update \
    && yum install -y unzip tar gzip bzip2-devel ed gcc gcc-c++ gcc-gfortran \
    less libcurl-devel openssl openssl-devel readline-devel xz-devel \
    zlib-devel glibc-static zlib-static \
    && rm -rf /var/cache/yum

# Graal VM
ENV GRAAL_VERSION 21.0.2
ENV ARCHITECTURE x64
ENV GRAAL_FILENAME graalvm-community-jdk-${GRAAL_VERSION}_linux-${ARCHITECTURE}_bin.tar.gz
RUN curl -4 -L https://github.com/graalvm/graalvm-ce-builds/releases/download/jdk-${GRAAL_VERSION}/${GRAAL_FILENAME} | tar -xvz
RUN mv graalvm-community-openjdk-${GRAAL_VERSION}* /usr/lib/graalvm
ENV JAVA_HOME /usr/lib/graalvm

# Gradle
ENV GRADLE_VERSION 7.4.1
ENV GRADLE_FOLDERNAME gradle-${GRADLE_VERSION}
ENV GRADLE_FILENAME gradle-${GRADLE_VERSION}-bin.zip
RUN curl -LO https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip
RUN unzip gradle-${GRADLE_VERSION}-bin.zip
RUN mv $GRADLE_FOLDERNAME /usr/lib/gradle
RUN ln -s /usr/lib/gradle/bin/gradle /usr/bin/gradle

VOLUME /project
WORKDIR /project

WORKDIR /lambda


